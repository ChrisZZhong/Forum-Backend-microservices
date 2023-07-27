package com.forum.authenticationservice.Entity;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean active;
    private Timestamp dateJoined;
    private String type;
    private String profileImageURL;
}
