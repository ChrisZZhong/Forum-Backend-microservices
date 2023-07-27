package com.forum.postandreplyservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostStatusRequest {
    private String postId;
    private String status;
}
