package com.forum.postandreplyservice.dto.response;

import com.forum.postandreplyservice.dto.ServiceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PostUserIdsResponse {
    private ServiceStatus status;
    private List<Long> userIds;
}
