package com.forum.authenticationservice.dto.response;

import com.forum.authenticationservice.Entity.User;
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
