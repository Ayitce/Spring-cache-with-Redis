package com.example.springcache.service;

import com.example.springcache.model.User;
import com.example.springcache.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    Optional<User> updateUserById(Long id, User user);
    boolean deleteUser(Long id);
}
