package com.forum.messageservice.dto.response;

import com.forum.messageservice.domain.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MessageResponse {
    private List<Message> messages;
}

