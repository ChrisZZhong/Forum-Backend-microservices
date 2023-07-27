package com.forum.userservice.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {
        @Email(message = "Email should be valid")
        @NotNull(message = "Email is required")
        private String email;
        @NotNull(message = "Password is required")
        private String password;

        private String firstname;
        private String lastname;
        private String profileImageURL;
}
