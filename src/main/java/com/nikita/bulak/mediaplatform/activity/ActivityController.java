package com.nikita.bulak.mediaplatform.activity;

import com.nikita.bulak.mediaplatform.post.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    @GetMapping
    public List<PostDto> getActivity(@RequestParam(required = false, defaultValue = "0") Integer from,
                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false, defaultValue = "desc") String sort){
        return activityService.getActivity(from, size, sort);
    }
}
