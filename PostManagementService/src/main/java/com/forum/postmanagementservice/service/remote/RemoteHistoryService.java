package com.forum.postmanagementservice.service.remote;

import com.forum.postmanagementservice.config.FeignRequestInterceptor;
import com.forum.postmanagementservice.dto.request.HistoryRequest;
import com.forum.postmanagementservice.dto.response.MessageResponse;
import com.forum.postmanagementservice.entity.History;
import com.forum.postmanagementservice.security.AuthUserDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "history", configuration = FeignRequestInterceptor.class)
public interface RemoteHistoryService {
    @GetMapping("history/all")
    ResponseEntity<List<History>> getUserHistory();

    @PostMapping("history/")
    ResponseEntity<MessageResponse> addOrUpdateHistory(@Valid @RequestBody HistoryRequest request);
}
