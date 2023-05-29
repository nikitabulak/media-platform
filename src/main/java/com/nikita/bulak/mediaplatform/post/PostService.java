package com.nikita.bulak.mediaplatform.post;

import com.nikita.bulak.mediaplatform.post.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getUsersPosts(Long authorId, Integer from, Integer size);

    PostDto updatePost(Long postId, PostDto postDto);

    boolean deletePost(Long postId);
}
