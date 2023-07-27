package com.forum.postandreplyservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteSubReplyRequest {
    private String postId;
    private int replyIndex;
    private int subReplyIndex;
}
