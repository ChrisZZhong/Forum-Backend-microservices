package com.forum.postandreplyservice.dto.response;

import com.forum.postandreplyservice.dto.ServiceStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostPreviewResponse {
    private ServiceStatus status;
    private List<PostPreviewDTO> posts;
}