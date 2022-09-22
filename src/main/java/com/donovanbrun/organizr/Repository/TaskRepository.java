package com.donovanbrun.organizr.Repository;

import com.donovanbrun.organizr.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    public List<Task> getTasksByUserId(String userId);
}
