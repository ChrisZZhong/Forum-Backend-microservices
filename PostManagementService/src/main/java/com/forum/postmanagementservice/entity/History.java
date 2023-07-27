package com.forum.postmanagementservice.entity;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private Integer historyId;
    private Integer userId;
    private String postId;
    private String viewDate;
}