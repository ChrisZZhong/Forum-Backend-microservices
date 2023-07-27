package com.forum.userservice.dto.response;

import com.forum.userservice.domain.User;
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