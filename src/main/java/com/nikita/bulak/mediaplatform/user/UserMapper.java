package com.nikita.bulak.mediaplatform.user;

import com.nikita.bulak.mediaplatform.user.dto.UserDto;
import com.nikita.bulak.mediaplatform.user.model.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(),
                user.getUsername(),
                user.getAuthors().stream().map(User::getId).collect(Collectors.toSet()),
                user.getFriends().stream().map(User::getId).collect(Collectors.toSet()),
                user.getRequestedFriends().stream().map(User::getId).collect(Collectors.toSet()),
                user.getRequestingFriends().stream().map(User::getId).collect(Collectors.toSet()));
    }
}
