package com.forum.postandreplyservice.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostReply {
    private Long userId;
    private String commentText;
    private boolean isActive;
    private LocalDateTime dateCreated;
    private List<SubReply> subReplies;
}
