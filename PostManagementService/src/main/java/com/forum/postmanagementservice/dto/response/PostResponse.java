package com.forum.postmanagementservice.dto.response;

import com.forum.postmanagementservice.dto.ServiceStatus;
import com.forum.postmanagementservice.entity.Post;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private ServiceStatus status;
    private Post post;
}