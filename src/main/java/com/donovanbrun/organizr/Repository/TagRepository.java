package com.donovanbrun.organizr.Repository;

import com.donovanbrun.organizr.Entity.Tag;
import com.donovanbrun.organizr.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    List<Tag> findAllByUserAndNameIn(User u, List<String> names);

    Optional<Tag> findByNameAndUser(String name, User user);
}
