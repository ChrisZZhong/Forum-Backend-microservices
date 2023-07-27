package com.forum.postmanagementservice.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostHistoryRequest {
    private String keyword;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
