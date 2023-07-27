package com.forum.postandreplyservice.service;

import com.forum.postandreplyservice.entity.Post;
import com.forum.postandreplyservice.entity.PostReply;
import com.forum.postandreplyservice.entity.SubReply;
import com.forum.postandreplyservice.exception.*;
import com.forum.postandreplyservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPublishedPostPreviews(Long userId, String orderBy, boolean ascending){
        List<Post> posts = new ArrayList<>();

        if (userId != null) {
            if (orderBy.equals("createTime"))
                posts = getAllPostsByStatusByUserIdOrderByCreateTime("published", userId, ascending);
            else if (orderBy.equals("replies"))
                posts = getAllPublishedPostsByUserIdOrderByReplies(userId, ascending);
        } else {
            if (orderBy.equals("createTime"))
                posts = getAllPostsByStatusOrderByCreateTime("published", ascending);
            else if (orderBy.equals("replies"))
                posts = getAllPublishedPostsOrderByReplies(ascending);
        }

        return posts;
    }

    public List<Post> getAllUnpublishedOrHiddenOrDeletedOrBannedPostPreviewsByUserId(Long userId, String status){
        return getAllPostsByStatusByUserIdOrderByCreateTime(status, userId, false);
    }

    public List<Post> getAllDeletedOrBannedPostPreviews(String status){
        return getAllPostsByStatusOrderByCreateTime(status, false);
    }

    public List<Post> getAllPostsByStatusOrderByCreateTime(String status, boolean ascending) {
        Sort.Direction sortDirection = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortByCreateTime = Sort.by(sortDirection, "dateCreated");
        return postRepository.findByStatus(status, sortByCreateTime);
    }

    public List<Post> getAllPublishedPostsOrderByReplies(boolean ascending) {
        List<Post> publishedPosts = postRepository.findByStatus("published");
        publishedPosts.sort((post1, post2) -> {
            int replies1 = post1.getPostReplies().size();
            int replies2 = post2.getPostReplies().size();
            return ascending ? Integer.compare(replies1, replies2) : Integer.compare(replies2, replies1);
        });
        return publishedPosts;
    }

    public List<Post> getAllPostsByStatusByUserIdOrderByCreateTime(String status, Long userId, boolean ascending) {
        Sort.Direction sortDirection = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortByCreateTime = Sort.by(sortDirection, "dateCreated");
        return postRepository.findByUserIdAndStatus(userId, status, sortByCreateTime);
    }

    public List<Post> getAllPublishedPostsByUserIdOrderByReplies(Long userId, boolean ascending) {
        List<Post> publishedPosts = postRepository.findByUserIdAndStatus(userId, "published");
        publishedPosts.sort((post1, post2) -> {
            int replies1 = post1.getPostReplies().size();
            int replies2 = post2.getPostReplies().size();
            return ascending ? Integer.compare(replies1, replies2) : Integer.compare(replies2, replies1);
        });
        return publishedPosts;
    }

    public List<Post> getTopPostsByUserIdOrderByReplies(Long userId, int limit) {
        List<Post> publishedPosts = postRepository.findByUserId(userId);
        publishedPosts.sort((post1, post2) -> {
            int replies1 = post1.getPostReplies().size();
            int replies2 = post2.getPostReplies().size();
            return Integer.compare(replies2, replies1); // Sort in descending order
        });
        return publishedPosts.stream().limit(limit).collect(Collectors.toList());
    }

    public List<Post> getAllPublishedPostsByIdsWithFilter(List<String> postIds, List<String> viewDates, String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        //List<Post> publishedPosts = postRepository.findAllByIdAndStatus(postIds, "published");
        List<Post> publishedPosts = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < postIds.size(); i++) {
            Post post = postRepository.findById(postIds.get(i)).orElse(null);
            LocalDateTime dateTime = LocalDateTime.parse(viewDates.get(i), formatter);
            post.setDateCreated(dateTime);
            publishedPosts.add(post);
        }
        List<Post> filteredPosts = new ArrayList<>();

        // Filter by keyword
        if (keyword != null && !keyword.isEmpty()) {
            String lowercaseKeyword = keyword.toLowerCase();
            for (Post post : publishedPosts) {
                if (post.getTitle().toLowerCase().contains(lowercaseKeyword)) {
                    filteredPosts.add(post);
                }
            }
        } else {
            filteredPosts.addAll(publishedPosts);
        }

        // Filter by date range
        if (startDate != null && endDate != null) {
            filteredPosts.removeIf(post -> post.getDateCreated().isBefore(startDate) || post.getDateCreated().isAfter(endDate));
        }

        return filteredPosts;
    }

    public Post getPostById(String id, long userId, boolean isAdmin) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null)
            throw new PostNotFoundException("Post not found.");

        if (post.getStatus().equals("unpublished") || post.getStatus().equals("hidden"))
        {
            if (post.getUserId() != userId)
                throw new UnauthorizedPostAccessException("You are not owner of the post. Access denied.");
        }

        if (post.getStatus().equals("deleted") || post.getStatus().equals("banned"))
        {
            if (!isAdmin) {
                if (post.getUserId() != userId)
                    throw new UnauthorizedPostAccessException("You are not owner of the post nor admin. Access denied.");
            }
        }

        if (post.getUserId() != userId && !isAdmin)
        {
            for (PostReply reply : post.getPostReplies())
            {
                if (!reply.isActive())
                {
                    reply.setCommentText("");
                }
                for (SubReply subReply : reply.getSubReplies())
                {
                    if (!subReply.isActive())
                    {
                        subReply.setCommentText("");
                    }
                }
            }
        }

        return post;
    }

    public Post createNewPost(long userId, String title, String content, String status) {
        if (!(status.equals("unpublished") || status.equals("published")))
            throw new InvalidPostStatusException("Cannot create a new post not unpublished or published.");

        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(title);
        post.setContent(content);
        post.setArchived(false);
        post.setStatus(status);
        post.setDateCreated(LocalDateTime.now());
        post.setDateModified(LocalDateTime.now());
        post.setImages(new ArrayList<>());
        post.setAttachments(new ArrayList<>());
        post.setPostReplies(new ArrayList<>());
        postRepository.save(post);
        return postRepository.findTopByUserIdOrderByDateCreatedDesc(userId).orElse(null);
    }

    public void editPostById(String postId, long userId, String newTitle, String newContent, List<String> images, List<String> attachments) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            if (post.getUserId() == userId) {
                if (post.getStatus().equals("banned") || post.getStatus().equals("deleted"))
                    throw new InvalidStatusUpdateException("Cannot edit a banned or deleted post.");
                post.setTitle(newTitle);
                post.setContent(newContent);
                if (!images.isEmpty() || !attachments.isEmpty())
                    post.setDateCreated(LocalDateTime.now());
                post.setDateModified(LocalDateTime.now());
                post.setImages(images);
                post.setAttachments(attachments);
                postRepository.save(post);
            }
            else {
                throw new UnauthorizedPostStatusUpdateException("You are not owner of the post. Edit denied.");
            }
        }
        else {
            throw new PostNotFoundException("Post not found.");
        }
    }

    public void modifyPostById(String postId, long userId, String newTitle, String newContent) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            if (post.getUserId() == userId) {
                if (post.getStatus().equals("banned") || post.getStatus().equals("deleted"))
                    throw new InvalidStatusUpdateException("Cannot edit a banned or deleted post.");
                post.setTitle(newTitle);
                post.setContent(newContent);
                post.setDateModified(LocalDateTime.now());
                postRepository.save(post);
            }
            else {
                throw new UnauthorizedPostStatusUpdateException("You are not owner of the post. Edit denied.");
            }
        }
        else {
            throw new PostNotFoundException("Post not found.");
        }
    }

    public void replyPostById(String postId, long userId, String comment) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            if (!post.getStatus().equals("published"))
                throw new InvalidReplyException("Cannot reply to a post not public.");
            if (post.isArchived())
                throw new InvalidReplyException("Cannot reply to an archived post.");
            PostReply reply = new PostReply();
            reply.setUserId(userId);
            reply.setCommentText(comment);
            reply.setActive(true);
            reply.setDateCreated(LocalDateTime.now());
            reply.setSubReplies(new ArrayList<>());
            post.getPostReplies().add(reply);
            postRepository.save(post);
        }
        else {
            throw new PostNotFoundException("Post not found.");
        }
    }

    public void replySubPostById(String postId, int replyIndex, long userId, String comment) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null && replyIndex >= 0 && replyIndex < post.getPostReplies().size()) {
            if (!post.getStatus().equals("published"))
                throw new InvalidReplyException("Cannot reply to a post not public.");
            if (post.isArchived())
                throw new InvalidReplyException("Cannot reply to an archived post.");
            SubReply subReply = new SubReply();
            subReply.setUserId(userId);
            subReply.setCommentText(comment);
            subReply.setActive(true);
            subReply.setDateCreated(LocalDateTime.now());
            post.getPostReplies().get(replyIndex).getSubReplies().add(subReply);
            postRepository.save(post);
        }
        else {
            throw new PostNotFoundException("Post not found.");
        }
    }

    public void deleteReplyByIdAndIndex(String postId, int replyIndex, long userId, boolean isAdmin) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null && replyIndex >= 0 && replyIndex < post.getPostReplies().size()) {
            if (post.getUserId() == userId ||
                    post.getPostReplies().get(replyIndex).getUserId() == userId ||
                    isAdmin) {
                if (post.getStatus().equals("banned") || post.getStatus().equals("deleted"))
                    throw new InvalidReplyException("Cannot delete reply in a banned or deleted post.");
                if (post.getStatus().equals("hidden") && post.getUserId() != userId)
                    throw new InvalidReplyException("Cannot delete reply in a hidden post.");
                PostReply reply = post.getPostReplies().get(replyIndex);
                reply.setActive(false);
                postRepository.save(post);
            }
            else {
                throw new UnauthorizedPostStatusUpdateException("You are not authorized to delete replies. Deletion denied.");
            }
        }
        else {
            throw new PostNotFoundException("Post not found.");
        }
    }

    public void deleteSubReplyByIdAndIndex(String postId, int replyIndex, int subReplyIndex, long userId, boolean isAdmin) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null && replyIndex >= 0 && replyIndex < post.getPostReplies().size()) {
            PostReply parentReply = post.getPostReplies().get(replyIndex);
            if (subReplyIndex >= 0 && subReplyIndex < parentReply.getSubReplies().size()) {
                if (post.getUserId() == userId ||
                        parentReply.getSubReplies().get(subReplyIndex).getUserId() == userId ||
                        isAdmin) {
                    if (post.getStatus().equals("banned") || post.getStatus().equals("deleted"))
                        throw new InvalidReplyException("Cannot delete reply in a banned or deleted post.");
                    if (post.getStatus().equals("hidden") && post.getUserId() != userId)
                        throw new InvalidReplyException("Cannot delete reply in a hidden post.");
                    SubReply subReply = parentReply.getSubReplies().get(subReplyIndex);
                    subReply.setActive(false);
                    postRepository.save(post);
                }
                else {
                    throw new UnauthorizedPostStatusUpdateException("You are not authorized to delete replies. Deletion denied.");
                }
            }
            else {
                throw new PostNotFoundException("Post not found.");
            }
        }
        else {
            throw new PostNotFoundException("Post not found.");
        }
    }

    public void updateArchiveById(String postId, long userId, boolean isArchived) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            if (post.getUserId() == userId) {
                if (post.getStatus().equals("banned") || post.getStatus().equals("deleted") || post.getStatus().equals("unpublished"))
                    throw new InvalidStatusUpdateException("Cannot archive a banned or deleted or unpublished post.");
                post.setArchived(isArchived);
                postRepository.save(post);
            }
            else {
                throw new UnauthorizedPostStatusUpdateException("You are not owner of the post. Edit denied.");
            }
        }
        else {
            throw new PostNotFoundException("Post not found.");
        }
    }

    public void updateStatusById(String postId, long userId, String newStatus, boolean isAdmin) {
        if (!(newStatus.equals("published") ||
                (newStatus.equals("hidden")) ||
                (newStatus.equals("deleted")) ||
                (newStatus.equals("banned"))))
            throw new InvalidStatusUpdateException("Invalid state. Update denied.");
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            switch (post.getStatus()) {
                case "unpublished":
                    if (!newStatus.equals("published"))
                        throw new InvalidStatusUpdateException("Cannot update unpublished post to status other than published.");
                    if (post.getUserId() != userId)
                        throw new UnauthorizedPostStatusUpdateException("You are not owner of the post. Update denied.");
                    post.setDateCreated(LocalDateTime.now());
                    post.setDateModified(LocalDateTime.now());
                    break;
                case "published":
                    if (newStatus.equals("unpublished"))
                        throw new InvalidStatusUpdateException("Cannot update published post to unpublished.");
                    if (!newStatus.equals("banned") && post.getUserId() != userId)
                        throw new UnauthorizedPostStatusUpdateException("You are not owner of the post. Update denied.");
                    if (newStatus.equals("banned") && !isAdmin)
                        throw new UnauthorizedPostStatusUpdateException("You are not an admin. Update denied.");
                    break;
                case "hidden":
                    if (!newStatus.equals("published"))
                        throw new InvalidStatusUpdateException("Cannot update hidden post to status other than published.");
                    if (post.getUserId() != userId)
                        throw new UnauthorizedPostStatusUpdateException("You are not owner of the post. Update denied.");
                    break;
                case "deleted":
                    if (!newStatus.equals("published"))
                        throw new InvalidStatusUpdateException("Cannot update deleted post to status other than published.");
                    if (!isAdmin)
                        throw new UnauthorizedPostStatusUpdateException("You are not an admin. Update denied.");
                    break;
                case "banned":
                    if (post.getUserId() == userId && newStatus.equals("deleted"))
                        break;
                    if (!newStatus.equals("published"))
                        throw new InvalidStatusUpdateException("Cannot update banned post to status other than published.");
                    if (!isAdmin)
                        throw new UnauthorizedPostStatusUpdateException("You are not an admin. Update denied.");
                    break;
                default:
                    throw new InvalidStatusUpdateException("Invalid state. Update denied.");
            }
            post.setStatus(newStatus);
            postRepository.save(post);
        }
        else {
            throw new PostNotFoundException("Post not found.");
        }
    }
}
