package com.donovanbrun.organizr.Repository;

import com.donovanbrun.organizr.Entity.Task;
import com.donovanbrun.organizr.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> getTasksByUser(User user);
}
