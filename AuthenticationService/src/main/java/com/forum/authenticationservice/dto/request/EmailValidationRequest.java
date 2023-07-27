package com.forum.authenticationservice.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailValidationRequest {

    @NotNull(message = "Email is required")
    @Size(min = 6, max = 6, message = "code should be 6 characters long")
    public String validationToken;
}
