package com.nikita.bulak.mediaplatform.post.dto;

import lombok.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {
    @Positive
    private Long authorId;
    @Size(min = 1, max = 100)
    private String header;
    @Size(min = 1, max = 500)
    private String text;
    @Size(min = 1, max = 500)
    private String resourceURI;
}
