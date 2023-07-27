package com.forum.postandreplyservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class EditPostRequest {
    @NotNull(message = "Title is required")
    private String title;
    @NotNull(message = "Content is required")
    private String content;
    private String postId;
    private List<String> images;
    private List<String> attachments;
}
