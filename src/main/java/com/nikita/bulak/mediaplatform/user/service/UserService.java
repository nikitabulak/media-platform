package com.nikita.bulak.mediaplatform.user.service;

import com.nikita.bulak.mediaplatform.user.dto.UserDto;

import java.util.List;

public interface UserService {
    Boolean createFriendsRequest(Long requestingFriendId);

    List<UserDto> getIncomingFriendsRequests();

    List<UserDto> getOutgoingFriendsRequests();

    Boolean acceptFriendsRequest(Long acceptingFriendId);

    Boolean declineFriendsRequest(Long decliningFriendId);

    Boolean removeFriend(Long friendId);

    Boolean removeSubscription(Long authorId);
//    User getUserById(Long id);
//
//    User getUserByUsername(String username);
//
//    List<User> getAllUsers();
//
//    boolean deleteUser(Long id);
}
