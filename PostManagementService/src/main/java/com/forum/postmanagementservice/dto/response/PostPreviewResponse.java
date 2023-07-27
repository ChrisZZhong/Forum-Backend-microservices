package com.forum.postmanagementservice.dto.response;

import com.forum.postmanagementservice.dto.ServiceStatus;
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