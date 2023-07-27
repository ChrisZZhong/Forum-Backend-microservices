package com.forum.postandreplyservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReplyRequest {
    @NotNull(message = "Comment is required")
    private String comment;
    @NotNull(message = "PostId is required")
    private String postId;
}
