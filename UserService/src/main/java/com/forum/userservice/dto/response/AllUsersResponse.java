package com.forum.userservice.dto.response;

import com.forum.userservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllUsersResponse {
    private List<User> users;
}
