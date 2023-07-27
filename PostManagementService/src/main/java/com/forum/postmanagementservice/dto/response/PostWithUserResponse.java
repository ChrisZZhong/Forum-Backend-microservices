package com.forum.postmanagementservice.dto.response;

import com.forum.postmanagementservice.dto.ServiceStatus;
import com.forum.postmanagementservice.entity.Post;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostWithUserResponse {
    private ServiceStatus status;
    private Post post;
    private List<UserInfoDTO> users;
}