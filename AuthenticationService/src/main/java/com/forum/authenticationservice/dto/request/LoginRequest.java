package com.forum.authenticationservice.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "Email should be valid")
    @NotNull(message = "Email is required")
    @Size(min = 1, message = "email should be longer than 1 character")
    private String email;
    @NotNull(message = "Password is required")
    @Size(min = 1, message = "email should be longer than 1 character")
    private String password;

}
