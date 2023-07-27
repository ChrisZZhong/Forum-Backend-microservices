package com.forum.postmanagementservice.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostPreviewRequest {
    private Long userId;
    private String status;
    private String orderBy;
    private boolean asc;
}
