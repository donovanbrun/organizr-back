package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Entity.Task;
import com.donovanbrun.organizr.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/task")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(path = "status")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public void getStatus() {}

    @GetMapping(path = "user/{userId}")
    public List<Task> getTasksByUserId(@PathVariable String userId) {
        return this.taskService.getTasksByUserId(userId);
    }

    @PostMapping(path = "add")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task t = this.taskService.addTask(task);
        return new ResponseEntity<Task>(t, HttpStatus.CREATED);
    }

    @PutMapping(path = "update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        try {
            Task t = this.taskService.updateTask(task);
            return new ResponseEntity<Task>(t, HttpStatus.CREATED);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping(path = "delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID taskId) {
        try {
            this.taskService.deleteTask(taskId);
            return new ResponseEntity<String>("Task " + taskId + " deleted.", HttpStatus.ACCEPTED);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}