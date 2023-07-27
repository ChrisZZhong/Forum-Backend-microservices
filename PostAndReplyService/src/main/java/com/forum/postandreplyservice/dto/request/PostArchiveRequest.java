package com.forum.postandreplyservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostArchiveRequest {
    private String postId;
    private boolean archived;
}
