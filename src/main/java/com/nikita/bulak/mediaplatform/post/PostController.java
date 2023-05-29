package com.nikita.bulak.mediaplatform.post;

import com.nikita.bulak.mediaplatform.post.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public PostDto createNewPost(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }

    @GetMapping("/get/{userId}")
    public List<PostDto> getUsersPosts(@PathVariable Long userId,
                                       @RequestParam(required = false, defaultValue = "0") Integer from,
                                       @RequestParam(required = false, defaultValue = "10") Integer size) {
        return postService.getUsersPosts(userId, from, size);
    }

    @PatchMapping("/update")
    public PostDto updateExistingPost(@RequestParam Long postId, @RequestBody PostDto postDto) {
        return postService.updatePost(postId, postDto);
    }

    @DeleteMapping("/delete")
    public boolean deleteExistingPost(@RequestParam Long postId){
        return postService.deletePost(postId);
    }
}
