package com.nikita.bulak.mediaplatform.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class SignupRequestDto {
    @Size(min = 5, max = 100)
    private String username;
    @Size(min = 10, max = 100)
    private String password;
    private Set<String> roles;
    @Email
    @Size(min = 5, max = 100)
    private String email;
}
