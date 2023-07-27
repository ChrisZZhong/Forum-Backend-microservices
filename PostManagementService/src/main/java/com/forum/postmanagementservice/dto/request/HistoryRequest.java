package com.forum.postmanagementservice.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRequest {
    private int userId;
    private String postId;
}
