package com.donovanbrun.organizr.Repository;

import com.donovanbrun.organizr.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u.username " +
            "from User u " +
            "where u.id = :userId")
    Optional<String> getUsernameById(UUID userId);

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findUserByEmail(String email);
}