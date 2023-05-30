package com.nikita.bulak.mediaplatform.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserDto {
    private Long id;
    private String username;
}
