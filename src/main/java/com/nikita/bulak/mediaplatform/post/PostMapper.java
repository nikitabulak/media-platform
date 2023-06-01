package com.nikita.bulak.mediaplatform.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikita.bulak.mediaplatform.exception.WrongPostDataException;
import com.nikita.bulak.mediaplatform.post.dto.PostDto;
import com.nikita.bulak.mediaplatform.post.model.Post;
import com.nikita.bulak.mediaplatform.user.model.User;

import javax.validation.Valid;
import java.time.LocalDateTime;

public class PostMapper {
    public static Post toNewPost(PostDto postDto, User author, String imageFilePath, LocalDateTime createdOn) {
        return new Post(0L,
                author,
                postDto.getHeader(),
                postDto.getText(),
                imageFilePath,
                createdOn);
    }

    public static PostDto toPostDto(Post post, String resourceURI) {
        return new PostDto(post.getAuthor().getId(), post.getHeader(), post.getText(), resourceURI);
    }

    public static PostDto toPostDtoFromString(String postDtoString) {
        @Valid
        PostDto postDto;
        try {
            postDto = new ObjectMapper().readValue(postDtoString, PostDto.class);
        } catch (JsonProcessingException e) {
            throw new WrongPostDataException("Post data has wrong structure: \n" + postDtoString);
        }
        return postDto;
    }
}
