package com.forum.postmanagementservice.dto.response;

import com.forum.postmanagementservice.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse {
    private String message;
    private String status;
    private List<User> users;
}