package com.forum.postmanagementservice.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String profileImageURL;
}
