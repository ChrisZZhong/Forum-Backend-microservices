package com.forum.postmanagementservice.controller;

import com.forum.postmanagementservice.dto.request.PostHistoryRequest;
import com.forum.postmanagementservice.dto.request.PostPreviewRequest;
import com.forum.postmanagementservice.security.AuthUserDetail;
import com.forum.postmanagementservice.service.PostManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class PostManagementController {
    private PostManagementService postManagementService;

    @Autowired
    public void setPostManagementService(PostManagementService postManagementService) {
        this.postManagementService = postManagementService;
    }

    @PostMapping("previews/published")
    @PreAuthorize("hasAuthority('unverified') or hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    ResponseEntity getAllPublishedPostPreviews(@Valid @RequestBody PostPreviewRequest request) {
        return ResponseEntity.ok(postManagementService.getAllPublishedPostPreviews(request));
    }

    @PostMapping("previews/user")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    ResponseEntity getAllUnpublishedOrHiddenOrDeletedOrBannedPostPreviewsOfOwner(@Valid @RequestBody PostPreviewRequest request) {
        return ResponseEntity.ok(postManagementService.getAllUnpublishedOrHiddenOrDeletedOrBannedPostPreviewsOfOwner(request));
    }

    @PostMapping("previews/admin")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('super')")
    ResponseEntity getAllDeletedOrBannedPostPreviews(@Valid @RequestBody PostPreviewRequest request) {
        return ResponseEntity.ok(postManagementService.getAllDeletedOrBannedPostPreviews(request));
    }

    @PostMapping("history")
    @PreAuthorize("hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    ResponseEntity getAllViewHistoryPosts(@RequestBody PostHistoryRequest request) {
        return ResponseEntity.ok(postManagementService.getAllViewHistoryPosts(request));
    }

    @GetMapping("{postId}")
    @PreAuthorize("hasAuthority('unverified') or hasAuthority('normal') or hasAuthority('admin') or hasAuthority('super')")
    ResponseEntity getPostById(@PathVariable String postId,
                               @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        return ResponseEntity.ok(postManagementService.getPostById(postId, authUserDetail));
    }
}
