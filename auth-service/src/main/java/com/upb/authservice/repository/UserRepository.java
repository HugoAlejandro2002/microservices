package com.upb.authservice.repository;

import com.upb.authservice.entity.ERole;
import com.upb.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    User findByRole(ERole role);
}
