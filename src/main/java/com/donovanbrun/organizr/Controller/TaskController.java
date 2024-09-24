package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Service.TaskService;
import com.donovanbrun.organizr.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getTasksByWorkspace(@AuthenticationPrincipal User user, @RequestParam("workspace") UUID workspaceId) {
        return ResponseEntity.ok(this.taskService.getTasksByWorkspace(user, workspaceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        try {
            return ResponseEntity.ok(this.taskService.getTaskById(id, user));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping()
    public ResponseEntity addTask(@AuthenticationPrincipal User user, @RequestBody TaskDTO task) {
        try {
            TaskDTO t = this.taskService.addTask(task, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(t);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping()
    public ResponseEntity updateTask(@AuthenticationPrincipal User user, @RequestBody TaskDTO task) {
        try {
            TaskDTO t = this.taskService.updateTask(task, user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(t);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/{taskId}")
    public ResponseEntity<String> deleteTask(@AuthenticationPrincipal User user, @PathVariable UUID taskId) {
        try {
            this.taskService.deleteTask(taskId, user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Task " + taskId + " deleted.");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/export")
    public ResponseEntity exportCSV(@AuthenticationPrincipal User user, HttpServletResponse servletResponse, @RequestParam("workspace") UUID workspaceId) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"tasks.csv\"");
        try {
            taskService.exportCSV(servletResponse.getWriter(), user, workspaceId);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}