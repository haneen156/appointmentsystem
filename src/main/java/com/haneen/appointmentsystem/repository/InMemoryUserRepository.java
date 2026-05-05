package com.haneen.appointmentsystem.repository;

import com.haneen.appointmentsystem.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryUserRepository implements UserRepository{

    private Map<Long, User> storage = new HashMap<>();

    private Long idCounter = 1L;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public User save(User user) {
        // if new user → assign ID
        if(user.getId() == null){
            user.setId(idCounter++);
        }
        // store or update user
        storage.put(user.getId(), user);

        return user;
    }
}
