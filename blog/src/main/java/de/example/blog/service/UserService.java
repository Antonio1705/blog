package de.example.blog.service;

import de.example.blog.entity.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> findByEmail(String email);
}
