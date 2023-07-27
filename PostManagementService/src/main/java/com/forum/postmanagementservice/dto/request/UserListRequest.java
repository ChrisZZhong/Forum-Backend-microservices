package com.forum.postmanagementservice.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListRequest {
    @NotNull
    private List<Integer> users;
}
