package com.forum.userservice.dto.request;

import com.forum.userservice.domain.User;
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
