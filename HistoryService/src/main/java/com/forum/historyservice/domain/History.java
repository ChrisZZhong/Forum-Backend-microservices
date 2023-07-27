package com.forum.historyservice.domain;

import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="History")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Integer historyId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "post_id")
    private String postId;

    @Column(name = "view_date")
    private String viewDate;
}