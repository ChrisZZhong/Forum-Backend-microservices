package com.forum.emailservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailValidationMessage {
    public String email;
    public String message;
}
