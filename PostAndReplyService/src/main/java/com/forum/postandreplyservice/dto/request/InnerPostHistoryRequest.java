package com.forum.postandreplyservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InnerPostHistoryRequest {
    private List<String> postIds;
    private List<String> viewDates;
    private String keyword;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
