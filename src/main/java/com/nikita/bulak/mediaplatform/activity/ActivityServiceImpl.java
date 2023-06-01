package com.nikita.bulak.mediaplatform.activity;

import com.nikita.bulak.mediaplatform.pageable.OffsetLimitPageable;
import com.nikita.bulak.mediaplatform.post.PostMapper;
import com.nikita.bulak.mediaplatform.post.PostRepository;
import com.nikita.bulak.mediaplatform.post.dto.PostDto;
import com.nikita.bulak.mediaplatform.post.model.Post;
import com.nikita.bulak.mediaplatform.user.AuthUtils;
import com.nikita.bulak.mediaplatform.user.model.User;
import com.nikita.bulak.mediaplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public List<PostDto> getActivity(Integer from, Integer size, String sort) {
        User user = userRepository.findById(AuthUtils.getCurrentUserId()).get();
        Sort currentSort = Sort.by("creationDate");
        currentSort = (sort.equals("asc") || sort.equals("ascending")) ? currentSort.ascending() : currentSort.descending();
        List<Post> authorsPosts = postRepository.findByAuthorIdIn(
                user.getAuthors().stream()
                        .map(User::getId)
                        .collect(Collectors.toList()),
                OffsetLimitPageable.of(from, size, currentSort));
        return authorsPosts.stream()
                .map(x -> PostMapper.toPostDto(x, x.getImageFilePath()))
                .collect(Collectors.toList());
    }
}
