package com.nikita.bulak.mediaplatform.user.repository;

import com.nikita.bulak.mediaplatform.user.model.ERole;
import com.nikita.bulak.mediaplatform.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(ERole name);
}
