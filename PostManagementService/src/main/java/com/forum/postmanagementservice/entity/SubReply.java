package com.forum.postmanagementservice.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubReply {
    private Long userId;
    private String commentText;
    private boolean isActive;
    private LocalDateTime dateCreated;
}
