package com.forum.postmanagementservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class Post {
    @Id
    private String id; // Use _id as id

    private Long userId;
    private String title;
    private String content;
    private boolean isArchived;
    private String status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private List<String> images;
    private List<String> attachments;
    private List<PostReply> postReplies;
}