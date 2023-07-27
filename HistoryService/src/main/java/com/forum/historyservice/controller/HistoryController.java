package com.forum.historyservice.controller;

import com.forum.historyservice.domain.History;
import com.forum.historyservice.dto.request.HistoryRequest;
import com.forum.historyservice.dto.response.MessageResponse;
import com.forum.historyservice.security.AuthUserDetail;
import com.forum.historyservice.security.JwtProvider;
import com.forum.historyservice.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class HistoryController {
    private final HistoryService historyService;
    private final JwtProvider jwtProvider;

    @Autowired
    public HistoryController(HistoryService historyService, JwtProvider jwtProvider) {
        this.historyService = historyService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/all")
    public ResponseEntity<List<History>> getUserHistory(@AuthenticationPrincipal AuthUserDetail authUserDetail) {
        Integer userId = authUserDetail.getUserId();
        System.out.println(userId);
        List<History> histories = historyService.getAllHistoryByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(histories);
    }

    @PostMapping("/history")
    public ResponseEntity<MessageResponse> addHistory(@Valid @RequestBody HistoryRequest request) {
        historyService.addHistoryToDatabase(request);
        return ResponseEntity.ok(
                MessageResponse.builder()
                        .status("success")
                        .message("History has been added")
                        .build()
        );
    }
//    @PatchMapping("/{postId}")
//    public ResponseEntity<MessageResponse> updateHistory(@PathVariable String postId) {
//        historyService.updateHistoryInDatabase(postId, userId);
//        return ResponseEntity.ok(
//                MessageResponse.builder()
//                        .status("success")
//                        .message("History has been updated")
//                        .build()
//        );
//    }
    @PostMapping
    public ResponseEntity<MessageResponse> addOrUpdateHistory(@Valid @RequestBody HistoryRequest request) {
        historyService.addOrUpdateHistory(request);
        return ResponseEntity.ok(
                MessageResponse.builder()
                        .status("success")
                        .message("History has been added")
                        .build()
        );
    }
}
