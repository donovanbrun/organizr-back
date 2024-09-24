package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.Task;
import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Entity.Workspace;
import com.donovanbrun.organizr.Repository.TaskRepository;
import com.donovanbrun.organizr.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final WorkspaceService workspaceService;

    public TaskDTO getTaskById(UUID id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow();

        if (!workspaceService.canView(user, task.getWorkspace())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot access this workspace");
        }

        return new TaskDTO(task);
    }

    public List<TaskDTO> getTasksByWorkspace(User user, UUID workspaceId) {
        Workspace workspace = workspaceService.getWorkspace(workspaceId)
                .orElseThrow();

        if (!workspaceService.canView(user, workspace)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot access this workspace");
        }

        List<Task> tasks = this.taskRepository.getTasksByWorkspace(workspace);
        return tasks.stream().map(TaskDTO::new).toList();
    }

    public TaskDTO addTask(TaskDTO taskDTO, User user) {
        Task task = new Task(taskDTO);
        if (task.getCreationDate() == null) task.setCreationDate(new Date());
        if (task.getUpdateDate() == null) task.setUpdateDate(new Date());
        task.setOwner(user);

        Workspace workspace = workspaceService.getWorkspace(taskDTO.getWorkspaceId())
                        .orElseThrow();

        task.setWorkspace(workspace);

        if (!workspaceService.canEdit(user, workspace)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot edit this workspace");
        }

        return new TaskDTO(
            taskRepository.save(task)
        );
    }

    public TaskDTO updateTask(TaskDTO taskDTO, User user) throws RuntimeException {
        Task task = taskRepository.findById(taskDTO.getId())
                .orElseThrow();

        if (!workspaceService.canEdit(user, task.getWorkspace())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot edit this workspace");
        }

        task.setTitle(taskDTO.getTitle());
        task.setDeadline(taskDTO.getDeadline());
        task.setDescription(taskDTO.getDescription());
        task.setUpdateDate(new Date());

        return new TaskDTO(this.taskRepository.save(task));
    }

    public void deleteTask(UUID taskId, User user) throws RuntimeException {
        Task task = this.taskRepository.findById(taskId)
                .orElseThrow();

        if (!workspaceService.canEdit(user, task.getWorkspace())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot edit this workspace");
        }

        taskRepository.delete(task);
    }

    public void exportCSV(PrintWriter writer, User user, UUID workspaceId) {

        Workspace workspace = workspaceService.getWorkspace(workspaceId)
                .orElseThrow();

        if (!workspaceService.canView(user, workspace)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot view this workspace");
        }

        List<Task> tasks = this.taskRepository.getTasksByWorkspace(workspace);

        try (CSVPrinter csv = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csv.printRecord("id","name","userId","status","description","deadline","creationDate","modificationDate","tags");
            for (Task task : tasks) {
                csv.printRecord(task.getId(),
                        task.getTitle(),
                        task.getOwner().getId(),
                        task.getStatus(),
                        task.getDescription(),
                        task.getDeadline(),
                        task.getCreationDate(),
                        task.getUpdateDate()
                );
            }
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while exporting tasks in CSV");
        }
    }
}
