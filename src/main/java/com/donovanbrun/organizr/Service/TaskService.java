package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.Task;
import com.donovanbrun.organizr.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return this.taskRepository.save(task);
    }

    public Task updateTask(Task task) {
        Task t;
        if (this.taskRepository.findById(task.getId()).isPresent()) {
            t = this.taskRepository.save(task);
            return t;
        }
        else throw new RuntimeException("Task doesn't exist");
    }

    public void deleteTask(Integer taskId) {
        if (this.taskRepository.findById(taskId).isPresent()) {
            this.taskRepository.deleteById(taskId);
        }
        else throw new RuntimeException("Task doesn't exist");
    }
}
