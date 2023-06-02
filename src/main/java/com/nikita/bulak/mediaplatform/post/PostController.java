package com.nikita.bulak.mediaplatform.post;

import com.nikita.bulak.mediaplatform.post.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public PostDto createNewPost(@RequestParam String postDtoString,
                                 @RequestParam(required = false) MultipartFile file) {
        return postService.createPost(postDtoString, file);
    }

    @GetMapping("/{userId}")
    public List<PostDto> getUsersPosts(@PathVariable Long userId,
                                       @RequestParam(required = false, defaultValue = "0") Integer from,
                                       @RequestParam(required = false, defaultValue = "10") Integer size) {
        return postService.getUsersPosts(userId, from, size);
    }

    @PatchMapping
    public PostDto updateExistingPost(@RequestParam Long postId,
                                      @RequestParam String postDtoString,
                                      @RequestParam(required = false) MultipartFile file) {
        return postService.updatePost(postId, postDtoString, file);
    }

    @DeleteMapping
    public boolean deleteExistingPost(@RequestParam Long postId) {
        return postService.deletePost(postId);
    }
}
