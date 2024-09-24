package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Service.UserService;
import com.donovanbrun.organizr.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUser(user));
    }
}
