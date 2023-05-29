package com.nikita.bulak.mediaplatform.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private Set<String> roles;
    private String email;
}
