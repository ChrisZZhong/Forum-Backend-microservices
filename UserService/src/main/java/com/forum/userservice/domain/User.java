package com.forum.userservice.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="user")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private boolean active;
    private LocalDateTime dateJoined;
    private String type;
    private String profileImageURL;
}
