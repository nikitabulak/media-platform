package com.nikita.bulak.mediaplatform.user.service;

import com.nikita.bulak.mediaplatform.user.model.User;
import com.nikita.bulak.mediaplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с username %s не найден", username)));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
