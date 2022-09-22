package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.Task;
import com.donovanbrun.organizr.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return this.taskRepository.findAll();
    }

    public List<Task> getTasksByUserId(String userId) {
        return this.taskRepository.getTasksByUserId(userId);
    }

    public Task addTask(Task task) {
        if (task.getCreationDate() == null) task.setCreationDate(new Date());
        if (task.getModificationDate() == null) task.setModificationDate(new Date());
        return this.taskRepository.save(task);
    }

    public Task updateTask(Task task) throws RuntimeException {
        Task t;
        if (this.taskRepository.findById(task.getId()).isPresent()) {
            if (task.getModificationDate() == null) task.setModificationDate(new Date());
            t = this.taskRepository.save(task);
            return t;
        }
        else throw new RuntimeException("Task doesn't exist");
    }

    public void deleteTask(UUID taskId) throws RuntimeException {
        if (this.taskRepository.findById(taskId).isPresent()) {
            this.taskRepository.deleteById(taskId);
        }
        else throw new RuntimeException("Task doesn't exist");
    }
}
