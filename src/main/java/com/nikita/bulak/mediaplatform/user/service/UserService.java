package com.nikita.bulak.mediaplatform.user.service;

import com.nikita.bulak.mediaplatform.user.model.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    boolean deleteUser(Long id);
}
