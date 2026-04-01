package com.haneen.appointmentsystem.repository;

import com.haneen.appointmentsystem.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    User save(User user);
}
