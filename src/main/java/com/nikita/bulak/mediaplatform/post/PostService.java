package com.nikita.bulak.mediaplatform.post;

import com.nikita.bulak.mediaplatform.post.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostDto createPost(String postDtoString, MultipartFile file);

    List<PostDto> getUsersPosts(Long authorId, Integer from, Integer size);

    PostDto updatePost(Long postId, String postDtoString, MultipartFile file);

    boolean deletePost(Long postId);
}
