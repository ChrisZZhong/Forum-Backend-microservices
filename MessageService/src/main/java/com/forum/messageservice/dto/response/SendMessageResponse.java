package com.forum.messageservice.dto.response;

import com.forum.messageservice.domain.Message;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageResponse {
    private String status;
    private String response;
    private Message message;
}
