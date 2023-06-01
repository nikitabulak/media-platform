package com.nikita.bulak.mediaplatform.friendship;

import com.nikita.bulak.mediaplatform.user.dto.UserDto;
import com.nikita.bulak.mediaplatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/friends/requests")
@RequiredArgsConstructor
public class FriendshipController {
    private final UserService userService;

    @PostMapping("/new")
    public Boolean createFriendsRequest(@RequestParam Long requestingFriendId) {
        return userService.createFriendsRequest(requestingFriendId);
    }

    @GetMapping("/get")
    public UserDto getUserInfo() {
        return userService.getCurrentUser();
    }

    @GetMapping("/get/incoming")
    public List<UserDto> getIncomingFriendsRequests() {
        return userService.getIncomingFriendsRequests();
    }

    @GetMapping("/get/outgoing")
    public List<UserDto> getOutgoingFriendsRequests() {
        return userService.getOutgoingFriendsRequests();
    }

    @PatchMapping("/accept")
    public Boolean acceptFriendsRequest(@RequestParam Long acceptingFriendId) {
        return userService.acceptFriendsRequest(acceptingFriendId);
    }

    @PatchMapping("/decline")
    public Boolean declineFriendsRequest(@RequestParam Long decliningFriendId) {
        return userService.declineFriendsRequest(decliningFriendId);
    }
}
