package com.forum.postandreplyservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteReplyRequest {
    private String postId;
    private int replyIndex;
}
