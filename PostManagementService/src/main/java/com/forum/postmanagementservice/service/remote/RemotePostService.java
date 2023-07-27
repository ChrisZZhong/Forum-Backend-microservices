package com.forum.postmanagementservice.service.remote;

import com.forum.postmanagementservice.config.FeignRequestInterceptor;
import com.forum.postmanagementservice.dto.request.InnerPostHistoryRequest;
import com.forum.postmanagementservice.dto.request.PostPreviewRequest;
import com.forum.postmanagementservice.dto.response.PostPreviewResponse;
import com.forum.postmanagementservice.dto.response.PostResponse;
import com.forum.postmanagementservice.security.AuthUserDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(value = "post", configuration = FeignRequestInterceptor.class)
public interface RemotePostService {
    @PostMapping("post/previews/published")
    @PreAuthorize("hasAuthority('unverified') or hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    ResponseEntity<PostPreviewResponse> getAllPublishedPostPreviews(@Valid @RequestBody PostPreviewRequest request);

    @PostMapping("post/previews/user")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    ResponseEntity<PostPreviewResponse> getAllUnpublishedOrHiddenOrDeletedOrBannedPostPreviewsOfOwner(@Valid @RequestBody PostPreviewRequest request);

    @PostMapping("post/previews/admin")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('super')")
    ResponseEntity<PostPreviewResponse> getAllDeletedOrBannedPostPreviews(@Valid @RequestBody PostPreviewRequest request);

    @PostMapping("post/history")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    ResponseEntity<PostPreviewResponse> getAllViewHistoryPosts(@RequestBody InnerPostHistoryRequest request);

    @GetMapping("post/{postId}")
    @PreAuthorize("hasAuthority('unverified') or hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    ResponseEntity<PostResponse> getPostById(@PathVariable String postId);
}
