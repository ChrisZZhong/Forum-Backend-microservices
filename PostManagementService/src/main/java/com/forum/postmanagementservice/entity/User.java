package com.forum.postmanagementservice.entity;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Boolean active;
    private Timestamp dateJoined;
    private String type;
    private String profileImageURL;
}
