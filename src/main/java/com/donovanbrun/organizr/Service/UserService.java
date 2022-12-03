package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    public void signin(User user) {
        userRepository.save(user);
    }

    public String getUsernameById(UUID userId) {
        return userRepository.getUsernameById(userId);
    }

    public String login(User user) {
        User u = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (u != null) {
            return u.getId().toString();
        }
        else throw new RuntimeException("Bad credentials");
    }
}