package com.forum.userservice.dto.response;

import com.forum.userservice.domain.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String message;
    private String status;
    private User user;
}