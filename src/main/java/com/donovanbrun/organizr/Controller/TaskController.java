package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Service.TaskService;
import com.donovanbrun.organizr.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @GetMapping()
    public List<TaskDTO> getTasksByUserId(@RequestHeader UUID userId) {
        return this.taskService.getTasksByUserId(userId);
    }

    @GetMapping("get/{id}")
    public TaskDTO getTaskById(@RequestHeader UUID userId, @PathVariable UUID id) {
        return this.taskService.getTaskById(id, userId);
    }

    @PostMapping(path = "add")
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO task, @RequestHeader UUID userId) {
        TaskDTO t = this.taskService.addTask(task, userId);
        return new ResponseEntity<TaskDTO>(t, HttpStatus.CREATED);
    }

    @PutMapping(path = "update")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO task, @RequestHeader UUID userId) {
        try {
            TaskDTO t = this.taskService.updateTask(task, userId);
            return new ResponseEntity<TaskDTO>(t, HttpStatus.CREATED);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping(path = "delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID taskId, @RequestHeader UUID userId) {
        try {
            this.taskService.deleteTask(taskId, userId);
            return new ResponseEntity<String>("Task " + taskId + " deleted.", HttpStatus.ACCEPTED);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(path = "/export")
    public void exportCSV(HttpServletResponse servletResponse, @RequestHeader UUID userId) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"tasks.csv\"");
        taskService.exportCSV(servletResponse.getWriter(), userId);
    }

    /*
    @PostMapping(path = "/addTag")
    public void addTag(@RequestParam("idTask") UUID idTask, @RequestParam("tag") String tag, @RequestHeader UUID userId) {
        taskService.addTag(idTask, tag, userId);
    }
    */

    /*
    @PostMapping(path = "tag")
    public List<TaskDTO> getTasksByTags(@RequestBody List<String> tags, @RequestHeader UUID userId) {
        return taskService.getTasksByTags(tags, userId);
    }
     */
}