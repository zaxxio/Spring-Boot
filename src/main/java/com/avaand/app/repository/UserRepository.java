package com.avaand.app.repository;

import com.avaand.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(UUID uuid);
}
