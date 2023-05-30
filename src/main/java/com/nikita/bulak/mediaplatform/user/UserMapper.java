package com.nikita.bulak.mediaplatform.user;

import com.nikita.bulak.mediaplatform.user.dto.UserDto;
import com.nikita.bulak.mediaplatform.user.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user){
        return new UserDto(user.getId(), user.getUsername());
    }
}
