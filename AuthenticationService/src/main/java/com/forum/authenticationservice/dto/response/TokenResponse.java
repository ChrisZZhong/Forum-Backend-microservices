package com.forum.authenticationservice.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String status;
    private String message;
    private String token;
}
