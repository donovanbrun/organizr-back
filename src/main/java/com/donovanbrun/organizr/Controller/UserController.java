package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("signin")
    public void signin(@RequestBody User user) {
        userService.signin(user);
    }

    @PostMapping("login")
    public String login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping("username")
    public String getUsername(@RequestHeader UUID userId) {
        return userService.getUsernameById(userId);
    }
}
