package com.nikita.bulak.mediaplatform.post;

import com.nikita.bulak.mediaplatform.exception.IllegalOperationException;
import com.nikita.bulak.mediaplatform.exception.PostNotFoundException;
import com.nikita.bulak.mediaplatform.exception.UserNotFoundException;
import com.nikita.bulak.mediaplatform.pageable.OffsetLimitPageable;
import com.nikita.bulak.mediaplatform.post.dto.PostDto;
import com.nikita.bulak.mediaplatform.post.model.Post;
import com.nikita.bulak.mediaplatform.user.model.User;
import com.nikita.bulak.mediaplatform.user.repository.UserRepository;
import com.nikita.bulak.mediaplatform.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        checkEditPermissions(postDto.getAuthorId());

        User user = userRepository.findById(postDto.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException("There is no user with id = " + postDto.getAuthorId()));
        Post post = PostMapper.toNewPost(postDto,
                user,
                LocalDateTime.now());
        return PostMapper.toPostDto(postRepository.save(post));
    }

    @Override
    public List<PostDto> getUsersPosts(Long authorId, Integer from, Integer size) {
        List<Post> posts = postRepository.findByAuthorId(authorId, OffsetLimitPageable.of(from, size));
        return posts.stream().map(PostMapper::toPostDto).collect(Collectors.toList());
    }

    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {
        checkEditPermissions(postDto.getAuthorId());

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("There is no post with id = " + postId));
        post.setHeader(postDto.getHeader());
        post.setText(postDto.getText());
        post.setImageName(postDto.getImageName());

        return PostMapper.toPostDto(postRepository.save(post));
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();

        if (!principal.getId().equals(postId)) {
            throw new IllegalOperationException("Post's id doesn't match with authorized user's id. " +
                    "You can only create, update and delete posts for authorized user.");
        }
    }
}
