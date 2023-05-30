package com.nikita.bulak.mediaplatform.activity;

import com.nikita.bulak.mediaplatform.post.dto.PostDto;

import java.util.List;

public interface ActivityService {
    List<PostDto> getActivity(Integer from, Integer size, String sort);
}
