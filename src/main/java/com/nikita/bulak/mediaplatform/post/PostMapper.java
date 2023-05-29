package com.nikita.bulak.mediaplatform.post;

import com.nikita.bulak.mediaplatform.post.dto.PostDto;
import com.nikita.bulak.mediaplatform.post.model.Post;
import com.nikita.bulak.mediaplatform.user.model.User;

import java.time.LocalDateTime;

public class PostMapper {
    public static Post toNewPost(PostDto postDto, User author, LocalDateTime createdOn) {
        return new Post(0L,
                author,
                postDto.getHeader(),
                postDto.getText(),
                postDto.getImageName(),
                createdOn);
    }
    public static PostDto toPostDto(Post post){
        return new PostDto(post.getAuthor().getId(), post.getHeader(), post.getText(), post.getImageName());
    }
}
