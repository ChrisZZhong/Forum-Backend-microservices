package com.forum.messageservice.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    private int userId;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Subject cannot be blank")
    String subject;

    @NotBlank(message = "Message cannot be blank")
    String message;

    String dateCreated;
}

