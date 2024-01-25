package com.example.springcache.service;

import com.example.springcache.model.User;
import com.example.springcache.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Cacheable(value = "user", key = "#id", unless = "#result==null")
    public Optional<User> getUserById(Long id) {
        log.info("Retrieve User ID: {}", id);
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @CachePut(value = "user", key = "#id")
    public Optional<User> updateUserById(Long id, User user) {
        User tempUser = userRepository.findById(id).orElse(null);
        if (tempUser == null) {
            return Optional.empty();
        }
        user.setId(id);
        userRepository.save(user);
        return Optional.of(userRepository.save(user));
    }

    @Override
    @CacheEvict(value = "user", key = "#id")
    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.info(e.toString());
            return false;
        }
    }
}
