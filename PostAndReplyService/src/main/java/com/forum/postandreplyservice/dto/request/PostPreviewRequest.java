package com.forum.postandreplyservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPreviewRequest {
    private Long userId;
    private String status;
    private String orderBy;
    private boolean asc;
}
