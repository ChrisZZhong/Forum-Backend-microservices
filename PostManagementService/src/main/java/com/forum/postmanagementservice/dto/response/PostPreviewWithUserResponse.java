package com.forum.postmanagementservice.dto.response;

import com.forum.postmanagementservice.dto.ServiceStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostPreviewWithUserResponse {
    private ServiceStatus status;
    private List<PostPreviewDTO> posts;
    private List<UserInfoDTO> users;
}