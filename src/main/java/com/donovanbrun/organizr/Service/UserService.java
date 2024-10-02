package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Entity.UserRole;
import com.donovanbrun.organizr.Repository.UserRepository;
import com.donovanbrun.organizr.dto.AuthenticationResponse;
import com.donovanbrun.organizr.dto.LoginRequest;
import com.donovanbrun.organizr.dto.RegisterRequest;
import com.donovanbrun.organizr.dto.UserDTO;
import com.donovanbrun.organizr.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public User getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    public void signin(User user) {
        userRepository.save(user);
    }

    public UserDTO getUser(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public AuthenticationResponse register(RegisterRequest registrationRequest) {
        var user = User.builder()
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(UserRole.USER)
                .build();
        userRepository.save(user);
        var token = jwtUtil.generateToken(user);
        var exp = jwtUtil.extractClaim(token, Claims.EXPIRATION, Long.class);
        return AuthenticationResponse.builder()
                .token(token)
                .expiration(exp)
                .build();
    }

    public AuthenticationResponse login(LoginRequest registrationRequest) {
        var a = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registrationRequest.getEmail(),
                        registrationRequest.getPassword()
                )
        );

        var user = userRepository.findUserByEmail(registrationRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var token = jwtUtil.generateToken(user);
        var exp = jwtUtil.extractClaim(token, Claims.EXPIRATION, Long.class);
        return AuthenticationResponse.builder()
                .token(token)
                .expiration(exp)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow();
    }
}