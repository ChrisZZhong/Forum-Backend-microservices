package com.forum.postandreplyservice.dto.response;

import com.forum.postandreplyservice.dto.ServiceStatus;
import com.forum.postandreplyservice.entity.Post;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private ServiceStatus status;
    private Post post;
}