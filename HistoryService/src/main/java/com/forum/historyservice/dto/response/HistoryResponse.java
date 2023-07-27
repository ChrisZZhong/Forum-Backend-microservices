package com.forum.historyservice.dto.response;

import com.forum.historyservice.domain.History;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class HistoryResponse {
    private List<History> histories;
}
