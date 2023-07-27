package com.forum.postandreplyservice.controller;

import com.forum.postandreplyservice.dto.ServiceStatus;
import com.forum.postandreplyservice.dto.request.*;
import com.forum.postandreplyservice.dto.response.NewPostResponse;
import com.forum.postandreplyservice.dto.response.PostPreviewDTO;
import com.forum.postandreplyservice.dto.response.PostPreviewResponse;
import com.forum.postandreplyservice.dto.response.PostResponse;
import com.forum.postandreplyservice.entity.Post;
import com.forum.postandreplyservice.security.AuthUserDetail;
import com.forum.postandreplyservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {
    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/test")
    public void test() {
        System.out.println("executed");
    }

    @PostMapping("/previews/published")
    @PreAuthorize("hasAuthority('unverified') or hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<PostPreviewResponse> getAllPublishedPostPreviews(@Valid @RequestBody PostPreviewRequest request){
        List<Post> posts = postService.getAllPublishedPostPreviews(request.getUserId(),
                request.getOrderBy(),
                request.isAsc());

        List<PostPreviewDTO> postPreviewDTOs = posts.stream()
                .map(post -> PostPreviewDTO.builder()
                        .postId(post.getId())
                        .userId(post.getUserId())
                        .date(post.getDateCreated())
                        .title(post.getTitle())
                        .build())
                .collect(Collectors.toList());

        PostPreviewResponse response = PostPreviewResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("All post previews found.")
                        .build())
                .posts(postPreviewDTOs)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/previews/user")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<PostPreviewResponse> getAllUnpublishedOrHiddenOrDeletedOrBannedPostPreviewsOfOwner(@Valid @RequestBody PostPreviewRequest request,
                                                                                                             @AuthenticationPrincipal AuthUserDetail authUserDetail){
        List<Post> posts = postService.getAllUnpublishedOrHiddenOrDeletedOrBannedPostPreviewsByUserId(authUserDetail.getUserId().longValue(), request.getStatus());

        List<PostPreviewDTO> postPreviewDTOs = posts.stream()
                .map(post -> PostPreviewDTO.builder()
                        .postId(post.getId())
                        .userId(post.getUserId())
                        .date(post.getDateCreated())
                        .title(post.getTitle())
                        .build())
                .collect(Collectors.toList());

        PostPreviewResponse response = PostPreviewResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("All post previews found.")
                        .build())
                .posts(postPreviewDTOs)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/previews/admin")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<PostPreviewResponse> getAllDeletedOrBannedPostPreviews(@Valid @RequestBody PostPreviewRequest request){
        List<Post> posts = postService.getAllDeletedOrBannedPostPreviews(request.getStatus());

        List<PostPreviewDTO> postPreviewDTOs = posts.stream()
                .map(post -> PostPreviewDTO.builder()
                        .postId(post.getId())
                        .userId(post.getUserId())
                        .date(post.getDateCreated())
                        .title(post.getTitle())
                        .build())
                .collect(Collectors.toList());

        PostPreviewResponse response = PostPreviewResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("All post previews found.")
                        .build())
                .posts(postPreviewDTOs)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/top")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<PostPreviewResponse> getTopRepliedPostPreviews(@RequestParam(value = "limit", defaultValue = "3", required = false) Integer limit,
                                                                         @AuthenticationPrincipal AuthUserDetail authUserDetail){
        List<Post> posts = postService.getTopPostsByUserIdOrderByReplies(authUserDetail.getUserId().longValue(), limit);

        List<PostPreviewDTO> postPreviewDTOs = posts.stream()
                .map(post -> PostPreviewDTO.builder()
                        .postId(post.getId())
                        .userId(post.getUserId())
                        .date(post.getDateCreated())
                        .title(post.getTitle())
                        .build())
                .collect(Collectors.toList());

        PostPreviewResponse response = PostPreviewResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("Top replied posts found.")
                        .build())
                .posts(postPreviewDTOs)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/history")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<PostPreviewResponse> getAllViewHistoryPosts(@RequestBody InnerPostHistoryRequest request) {
        System.out.println("NMD");
        List<Post> posts = postService.getAllPublishedPostsByIdsWithFilter(request.getPostIds(),
                request.getViewDates(),
                request.getKeyword(),
                request.getStartDate(),
                request.getEndDate());

        List<PostPreviewDTO> postPreviewDTOs = posts.stream()
                .map(post -> PostPreviewDTO.builder()
                        .postId(post.getId())
                        .userId(post.getUserId())
                        .date(post.getDateCreated())
                        .title(post.getTitle())
                        .build())
                .collect(Collectors.toList());

        PostPreviewResponse response = PostPreviewResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("All published history post previews found by post id.")
                        .build())
                .posts(postPreviewDTOs)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    @PreAuthorize("hasAuthority('unverified') or hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<PostResponse> getPostById(@PathVariable String postId,
                                                    @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        Collection<? extends GrantedAuthority> authorities = authUserDetail.getAuthorities();

        // Check if the user is admin
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("admin") ||
                        authority.getAuthority().equals("super"));

        Post post = postService.getPostById(postId, authUserDetail.getUserId(), isAdmin);

        PostResponse response = PostResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("Post found.")
                        .build())
                .post(post)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<NewPostResponse> createNewPost(@Valid @RequestBody NewPostRequest request,
                                                       @AuthenticationPrincipal AuthUserDetail authUserDetail){
        Post post = postService.createNewPost(authUserDetail.getUserId(),
                request.getTitle(),
                request.getContent(),
                request.getStatus());

        NewPostResponse response = NewPostResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("Post created.")
                        .build())
                .post(post)
                .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/edit")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<ServiceStatus> editPost(@Valid @RequestBody EditPostRequest request,
                                                  @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        postService.editPostById(request.getPostId(),
                authUserDetail.getUserId(),
                request.getTitle(),
                request.getContent(),
                request.getImages(),
                request.getAttachments());

        // Return success response
        return ResponseEntity.ok(ServiceStatus.builder()
                .success(true)
                .message("Post edited.")
                .build());
    }

    @PatchMapping("/modify")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<ServiceStatus> modifyPost(@Valid @RequestBody EditPostRequest request,
                                                    @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        postService.modifyPostById(request.getPostId(),
                authUserDetail.getUserId(),
                request.getTitle(),
                request.getContent());

        // Return success response
        return ResponseEntity.ok(ServiceStatus.builder()
                .success(true)
                .message("Post edited.")
                .build());
    }

    @PatchMapping("/reply")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<ServiceStatus> replyPost(@Valid @RequestBody ReplyRequest request,
                                                   @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        postService.replyPostById(request.getPostId(),
                authUserDetail.getUserId(),
                request.getComment());

        // Return success response
        return ResponseEntity.ok(ServiceStatus.builder()
                .success(true)
                .message("Post replied.")
                .build());
    }

    @PatchMapping("/subReply")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<ServiceStatus> replySubPost(@Valid @RequestBody SubReplyRequest request,
                                                      @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        postService.replySubPostById(request.getPostId(),
                request.getReplyIndex(),
                authUserDetail.getUserId(),
                request.getComment());

        // Return success response
        return ResponseEntity.ok(ServiceStatus.builder()
                .success(true)
                .message("Sub Post replied.")
                .build());
    }

    @PatchMapping("/delete/reply")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<ServiceStatus> deleteReply(@RequestBody DeleteReplyRequest request,
                                                     @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        Collection<? extends GrantedAuthority> authorities = authUserDetail.getAuthorities();

        // Check if the user is admin
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("admin") ||
                        authority.getAuthority().equals("super"));

        postService.deleteReplyByIdAndIndex(request.getPostId(),
                request.getReplyIndex(),
                authUserDetail.getUserId(),
                isAdmin);

        // Return success response
        return ResponseEntity.ok(ServiceStatus.builder()
                .success(true)
                .message("Reply deleted.")
                .build());
    }

    @PatchMapping("/delete/subreply")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<ServiceStatus> deleteSubReply(@RequestBody DeleteSubReplyRequest request,
                                                        @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        Collection<? extends GrantedAuthority> authorities = authUserDetail.getAuthorities();

        // Check if the user is admin
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("admin") ||
                        authority.getAuthority().equals("super"));

        postService.deleteSubReplyByIdAndIndex(request.getPostId(),
                request.getReplyIndex(),
                request.getSubReplyIndex(),
                authUserDetail.getUserId(),
                isAdmin);

        // Return success response
        return ResponseEntity.ok(ServiceStatus.builder()
                .success(true)
                .message("Sub reply deleted.")
                .build());
    }

    @PatchMapping("/update/archived")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<ServiceStatus> updateArchive(@RequestBody PostArchiveRequest request,
                                                       @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        postService.updateArchiveById(request.getPostId(),
                authUserDetail.getUserId(),
                request.isArchived());

        // Return success response
        return ResponseEntity.ok(ServiceStatus.builder()
                .success(true)
                .message("Post archive status updated.")
                .build());
    }

    @PatchMapping("/update/status")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    public ResponseEntity<ServiceStatus> updateStatus(@RequestBody PostStatusRequest request,
                                                      @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        Collection<? extends GrantedAuthority> authorities = authUserDetail.getAuthorities();

        // Check if the user is admin
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("admin") ||
                        authority.getAuthority().equals("super"));

        postService.updateStatusById(request.getPostId(),
                authUserDetail.getUserId(),
                request.getStatus(),
                isAdmin);

        // Return success response
        return ResponseEntity.ok(ServiceStatus.builder()
                .success(true)
                .message("Post status updated.")
                .build());
    }
}
