package com.forum.postandreplyservice.dto.response;

import com.forum.postandreplyservice.dto.ServiceStatus;
import com.forum.postandreplyservice.entity.Post;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewPostResponse {
    private ServiceStatus status;
    Post post;
}
