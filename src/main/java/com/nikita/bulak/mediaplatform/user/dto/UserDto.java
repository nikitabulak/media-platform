package com.nikita.bulak.mediaplatform.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@AllArgsConstructor
@ToString
public class UserDto {
    private Long id;
    private String username;
    private Set<Long> authors;
    private Set<Long> friends;
    private Set<Long> requestedFriends;
    private Set<Long> requestingFriends;
}
