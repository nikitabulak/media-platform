package com.nikita.bulak.mediaplatform.post;

import com.nikita.bulak.mediaplatform.exception.IllegalOperationException;
import com.nikita.bulak.mediaplatform.exception.PostNotFoundException;
import com.nikita.bulak.mediaplatform.exception.UserNotFoundException;
import com.nikita.bulak.mediaplatform.minio.service.MinioService;
import com.nikita.bulak.mediaplatform.pageable.OffsetLimitPageable;
import com.nikita.bulak.mediaplatform.post.dto.PostDto;
import com.nikita.bulak.mediaplatform.post.model.Post;
import com.nikita.bulak.mediaplatform.user.AuthUtils;
import com.nikita.bulak.mediaplatform.user.model.User;
import com.nikita.bulak.mediaplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MinioService storageService;

    @Override
    public PostDto createPost(String postDtoString, MultipartFile file) {
        PostDto postDto = PostMapper.toPostDtoFromString(postDtoString);
        checkEditPermissions(postDto.getAuthorId());

        User user = userRepository.findById(postDto.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException("There is no user with id = " + postDto.getAuthorId()));
        Post post = PostMapper.toNewPost(postDto, user, file == null ? "" : storageService.save(file), LocalDateTime.now());
        post = postRepository.save(post);
        return PostMapper.toPostDto(post, post.getImageFilePath());
    }


    @Override
    public List<PostDto> getUsersPosts(Long authorId, Integer from, Integer size) {
        List<Post> posts = postRepository.findByAuthorId(authorId, OffsetLimitPageable.of(from, size));
        return posts.stream()
                .map(x -> PostMapper.toPostDto(x, x.getImageFilePath()))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto updatePost(Long postId, String postDtoString, MultipartFile file) {
        PostDto postDto = PostMapper.toPostDtoFromString(postDtoString);
        checkEditPermissions(postDto.getAuthorId());

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("There is no post with id = " + postId));
        post.setHeader(postDto.getHeader());
        post.setText(postDto.getText());
        if (file != null) {
            post.setImageFilePath(storageService.save(file));

        }
        post = postRepository.save(post);

        return PostMapper.toPostDto(post, post.getImageFilePath());
    }

    @Override
    public boolean deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("There is no post with id = " + postId));
        checkEditPermissions(post.getAuthor().getId());
        postRepository.deleteById(postId);
        return postRepository.findById(postId).isEmpty();
    }

    private void checkEditPermissions(Long postId) {
        if (!AuthUtils.getCurrentUserId().equals(postId)) {
            throw new IllegalOperationException("Post's id doesn't match with authorized user's id. " +
                    "You can only create, update and delete posts for authorized user.");
        }
    }
}
