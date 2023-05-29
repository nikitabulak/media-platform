package com.nikita.bulak.mediaplatform.user.repository;

import com.nikita.bulak.mediaplatform.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
