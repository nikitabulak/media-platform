package com.nikita.bulak.mediaplatform.friendship;

import com.nikita.bulak.mediaplatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/friends/")
@RequiredArgsConstructor
public class FriendsController {
    private final UserService userService;

    @DeleteMapping("/{friendId}/remove/friend")
    public Boolean removeFriend(@PathVariable Long friendId) {
        return userService.removeFriend(friendId);
    }

    @DeleteMapping("/{authorId}/remove/subscription")
    public Boolean removeSubscription(@PathVariable Long authorId) {
        return userService.removeSubscription(authorId);
    }
}
