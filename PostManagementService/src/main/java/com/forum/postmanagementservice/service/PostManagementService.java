package com.forum.postmanagementservice.service;

import com.forum.postmanagementservice.dto.ServiceStatus;
import com.forum.postmanagementservice.dto.request.*;
import com.forum.postmanagementservice.dto.response.*;
import com.forum.postmanagementservice.entity.*;
import com.forum.postmanagementservice.exception.PostNotFoundException;
import com.forum.postmanagementservice.security.AuthUserDetail;
import com.forum.postmanagementservice.service.remote.RemoteHistoryService;
import com.forum.postmanagementservice.service.remote.RemotePostService;
import com.forum.postmanagementservice.service.remote.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostManagementService {
    private RemoteUserService userService;

    private RemoteHistoryService historyService;

    private RemotePostService postService;

    @Autowired
    public void setUserService(RemoteUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setHistoryService(RemoteHistoryService historyService) {
        this.historyService = historyService;
    }

    @Autowired
    public void setPostService(RemotePostService postService) {
        this.postService = postService;
    }

    public PostPreviewWithUserResponse getAllPublishedPostPreviews(PostPreviewRequest request) {
        ResponseEntity<PostPreviewResponse> postPreviewResponse = postService.getAllPublishedPostPreviews(request);

        List<Integer> userIds = postPreviewResponse.getBody().getPosts().stream()
                .map(PostPreviewDTO::getUserId)
                .distinct()
                .map(Long::intValue)
                .collect(Collectors.toList());

        UserListRequest userListRequest = UserListRequest.builder()
                .users(userIds)
                .build();

        UserListResponse userListResponse = userService.loadUsersByIdList(userListRequest);
        List<User> users = userListResponse.getUsers();
        List<UserInfoDTO> userInfoDTOs = users.stream()
                .map(user -> new UserInfoDTO(
                        user.getUserId().longValue(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getProfileImageURL()
                ))
                .collect(Collectors.toList());

        PostPreviewWithUserResponse response = PostPreviewWithUserResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("All post previews found.")
                        .build())
                .posts(postPreviewResponse.getBody().getPosts())
                .users(userInfoDTOs)
                .build();

        return response;
    }

    public PostPreviewWithUserResponse getAllUnpublishedOrHiddenOrDeletedOrBannedPostPreviewsOfOwner(PostPreviewRequest request) {
        ResponseEntity<PostPreviewResponse> postPreviewResponse = postService.getAllUnpublishedOrHiddenOrDeletedOrBannedPostPreviewsOfOwner(request);

        List<Integer> userIds = postPreviewResponse.getBody().getPosts().stream()
                .map(PostPreviewDTO::getUserId)
                .distinct()
                .map(Long::intValue)
                .collect(Collectors.toList());

        UserListRequest userListRequest = UserListRequest.builder()
                .users(userIds)
                .build();

        UserListResponse userListResponse = userService.loadUsersByIdList(userListRequest);
        List<User> users = userListResponse.getUsers();
        List<UserInfoDTO> userInfoDTOs = users.stream()
                .map(user -> new UserInfoDTO(
                        user.getUserId().longValue(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getProfileImageURL()
                ))
                .collect(Collectors.toList());

        PostPreviewWithUserResponse response = PostPreviewWithUserResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("All post previews found.")
                        .build())
                .posts(postPreviewResponse.getBody().getPosts())
                .users(userInfoDTOs)
                .build();

        return response;
    }

    public PostPreviewWithUserResponse getAllDeletedOrBannedPostPreviews(PostPreviewRequest request) {
        ResponseEntity<PostPreviewResponse> postPreviewResponse = postService.getAllDeletedOrBannedPostPreviews(request);

        List<Integer> userIds = postPreviewResponse.getBody().getPosts().stream()
                .map(PostPreviewDTO::getUserId)
                .distinct()
                .map(Long::intValue)
                .collect(Collectors.toList());

        UserListRequest userListRequest = UserListRequest.builder()
                .users(userIds)
                .build();

        UserListResponse userListResponse = userService.loadUsersByIdList(userListRequest);
        List<User> users = userListResponse.getUsers();
        List<UserInfoDTO> userInfoDTOs = users.stream()
                .map(user -> new UserInfoDTO(
                        user.getUserId().longValue(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getProfileImageURL()
                ))
                .collect(Collectors.toList());

        PostPreviewWithUserResponse response = PostPreviewWithUserResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("All post previews found.")
                        .build())
                .posts(postPreviewResponse.getBody().getPosts())
                .users(userInfoDTOs)
                .build();

        return response;
    }

    public PostPreviewWithUserResponse getAllViewHistoryPosts(PostHistoryRequest request) {
        ResponseEntity<List<History>> historyResponse = historyService.getUserHistory();
        List<History> historyPostList = historyResponse.getBody();
        List<String> postIds = historyPostList.stream()
                .map(History::getPostId)
                .collect(Collectors.toList());

        List<String> viewDates = historyPostList.stream()
                .map(History::getViewDate)
                .collect(Collectors.toList());

        InnerPostHistoryRequest innerPostHistoryRequest = InnerPostHistoryRequest.builder()
                .postIds(postIds)
                .viewDates(viewDates)
                .keyword(request.getKeyword())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        ResponseEntity<PostPreviewResponse> postPreviewResponse = postService.getAllViewHistoryPosts(innerPostHistoryRequest);

        List<Integer> userIds = postPreviewResponse.getBody().getPosts().stream()
                .map(PostPreviewDTO::getUserId)
                .distinct()
                .map(Long::intValue)
                .collect(Collectors.toList());

        UserListRequest userListRequest = UserListRequest.builder()
                .users(userIds)
                .build();

        UserListResponse userListResponse = userService.loadUsersByIdList(userListRequest);
        List<User> users = userListResponse.getUsers();
        List<UserInfoDTO> userInfoDTOs = users.stream()
                .map(user -> new UserInfoDTO(
                        user.getUserId().longValue(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getProfileImageURL()
                ))
                .collect(Collectors.toList());

        PostPreviewWithUserResponse response = PostPreviewWithUserResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("All view history preview found.")
                        .build())
                .posts(postPreviewResponse.getBody().getPosts())
                .users(userInfoDTOs)
                .build();

        return response;
    }

    public PostWithUserResponse getPostById(String postId,
                                            AuthUserDetail authUserDetail) {
        ResponseEntity<PostResponse> postResponse = postService.getPostById(postId);
        if (postResponse.getStatusCode().is2xxSuccessful())
            historyService.addOrUpdateHistory(new HistoryRequest(authUserDetail.getUserId(), postId));
        else
            throw new PostNotFoundException("Post not found.");

        Post post = postResponse.getBody().getPost();
        Set<Integer> userIds = new HashSet<>();
        userIds.add(post.getUserId().intValue());
        for (PostReply reply : post.getPostReplies())
        {
            userIds.add(reply.getUserId().intValue());
            for (SubReply subReply : reply.getSubReplies())
                userIds.add(subReply.getUserId().intValue());
        }

        UserListRequest userListRequest = UserListRequest.builder()
                .users(new ArrayList<>(userIds))
                .build();

        UserListResponse userListResponse = userService.loadUsersByIdList(userListRequest);
        List<User> users = userListResponse.getUsers();
        List<UserInfoDTO> userInfoDTOs = users.stream()
                .map(user -> new UserInfoDTO(
                        user.getUserId().longValue(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getProfileImageURL()
                ))
                .collect(Collectors.toList());

        PostWithUserResponse response = PostWithUserResponse.builder()
                .status(ServiceStatus.builder()
                        .success(true)
                        .message("Post found.")
                        .build())
                .post(post)
                .users(userInfoDTOs)
                .build();

        return response;
    }
}
