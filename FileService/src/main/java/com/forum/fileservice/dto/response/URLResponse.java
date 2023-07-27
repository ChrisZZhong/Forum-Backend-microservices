package com.forum.fileservice.dto.response;

import lombok.*;

import java.net.URL;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class URLResponse {
    private String status;
    private List<URL> urlList;
}
