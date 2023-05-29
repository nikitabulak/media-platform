package com.nikita.bulak.mediaplatform.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PostDto {
    private Long authorId;
    private String header;
    private String text;
    private String imageName;
}
