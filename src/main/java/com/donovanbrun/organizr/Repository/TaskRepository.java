package com.donovanbrun.organizr.Repository;

import com.donovanbrun.organizr.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    public List<Task> getTasksByUserId(String userId);
}
