package com.forum.messageservice.controller;

import com.forum.messageservice.domain.Message;
import com.forum.messageservice.dto.request.MessageRequest;
import com.forum.messageservice.dto.response.ChangeStatusResponse;
import com.forum.messageservice.dto.response.MessageResponse;
import com.forum.messageservice.dto.response.SendMessageResponse;
import com.forum.messageservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('admin', 'super')")
    public ResponseEntity<MessageResponse> getAllMessages() {
        return ResponseEntity.ok(
                MessageResponse.builder()
                        .messages(messageService.getAllMessages())
                        .build()
        );
    }
    @PostMapping
    public ResponseEntity<SendMessageResponse> sendMessage(@Valid @RequestBody MessageRequest request) {
        return ResponseEntity.ok(
                SendMessageResponse.builder()
                        .status("success")
                        .response("Message Sent!")
                        .message(messageService.sendMessage(request))
                        .build()
        );
    }
    @PatchMapping("/{messageId}")
    @PreAuthorize("hasAnyAuthority('admin', 'super')")
    public ResponseEntity<ChangeStatusResponse> changeStatus(@PathVariable int messageId) {
        return ResponseEntity.ok(
                ChangeStatusResponse.builder()
                        .status("success")
                        .response("Status Changed")
                        .message(messageService.changeStatus(messageId))
                        .build()
        );
    }
}
