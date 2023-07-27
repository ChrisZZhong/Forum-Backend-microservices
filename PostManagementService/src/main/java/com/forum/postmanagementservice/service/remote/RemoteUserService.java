package com.forum.postmanagementservice.service.remote;

import com.forum.postmanagementservice.config.FeignRequestInterceptor;
import com.forum.postmanagementservice.dto.request.UserListRequest;
import com.forum.postmanagementservice.dto.response.UserListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "user", configuration = FeignRequestInterceptor.class)
public interface RemoteUserService {
    @GetMapping("user/list")
    UserListResponse loadUsersByIdList(@Valid @RequestBody UserListRequest request);
}
