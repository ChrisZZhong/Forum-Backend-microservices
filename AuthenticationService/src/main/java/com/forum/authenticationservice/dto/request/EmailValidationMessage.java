package com.forum.authenticationservice.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailValidationMessage {
    public String email;
    public String message;
}
