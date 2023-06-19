package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.Tag;
import com.donovanbrun.organizr.Entity.Task;
import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Repository.TagRepository;
import com.donovanbrun.organizr.Repository.TaskRepository;
import com.donovanbrun.organizr.dto.TaskDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private UserService userService;
    private TagRepository tagRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.tagRepository = tagRepository;
    }

    public List<TaskDTO> getTasksByUserId(UUID userId) {
        User u = userService.getUserById(userId);
        if (u != null) {
            List<Task> tasks = this.taskRepository.getTasksByUser(u);
            ArrayList<TaskDTO> retTasks = new ArrayList<>(tasks.size());

            for (Task task : tasks) {
                retTasks.add(new TaskDTO(task));
            }
            return retTasks;
        }
        else throw new RuntimeException();
    }

    public TaskDTO addTask(TaskDTO taskDTO, UUID userId) {
        Task task = new Task(taskDTO);
        if (task.getCreationDate() == null) task.setCreationDate(new Date());
        if (task.getUpdateDate() == null) task.setUpdateDate(new Date());
        User u = userService.getUserById(userId);

        if (u != null) {
            task.setUser(u);

            /*for (String tag : taskDTO.getTags()) {
                this.addTag(task, tag);
            }*/
            
            Task t = this.taskRepository.save(task);
            return new TaskDTO(t);
        }
        else throw new RuntimeException();
    }

    public TaskDTO updateTask(TaskDTO taskDTO, UUID userId) throws RuntimeException {
        Task task = new Task(taskDTO);
        User u = userService.getUserById(userId);

        if (u != null) {
            if (this.taskRepository.findById(task.getId()).isPresent()) {
                task.setUser(u);

                /*for (String tag : taskDTO.getTags()) {
                    this.addTag(task, tag);
                }*/

                task.setUpdateDate(new Date());
                return new TaskDTO(this.taskRepository.save(task));
            }
            else throw new RuntimeException("Task doesn't exist");
        }
        else throw new RuntimeException("User doesn't exist or doesn't own this task");
    }

    public void deleteTask(UUID taskId, UUID userId) throws RuntimeException {
        Optional<Task> optTask = this.taskRepository.findById(taskId);
        if (optTask.isPresent()) {
            Task task = optTask.get();
            if (task.getUser().getId().equals(userId)) {
                this.taskRepository.deleteById(taskId);
            }
            else throw new RuntimeException("User doesn't own this task");
        }
        else throw new RuntimeException("Task doesn't exist");
    }

    public void exportCSV(PrintWriter writer, UUID userId) {
        User u = userService.getUserById(userId);
        if (u != null) {
            List<Task> tasks = this.taskRepository.getTasksByUser(u);

            try (CSVPrinter csv = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
                csv.printRecord("id","name","userId","status","description","deadline","creationDate","modificationDate","tags");

                for (Task task : tasks) {
                    /*StringBuilder tags = new StringBuilder();
                    for (int i = 0; i < task.getTags().size(); i++) {
                        tags.append(task.getTags().get(i).getName());
                        if (i != task.getTags().size()-1) tags.append(';');
                    }*/

                    csv.printRecord(task.getId(),
                            task.getName(),
                            task.getUser().getId(),
                            task.getStatus(),
                            task.getDescription(),
                            task.getDeadline(),
                            task.getCreationDate(),
                            task.getUpdateDate()
                            //tags.toString()
                    );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else throw new RuntimeException();
    }

    /*private void addTag(Task task, String tag) {

        tag = tag.trim().toLowerCase();
        Optional<Tag> optionalTag = tagRepository.findByNameAndUser(tag, task.getUser());
        Tag newTag = null;
        List<Tag> tags = task.getTags();

        if (optionalTag.isPresent()) {
            newTag = optionalTag.get();

            for (Tag t : tags) {
                if (newTag.getName().equalsIgnoreCase(t.getName()))
                    return;
            }
        }
        else {
            newTag = new Tag();
            newTag.setName(tag);
            newTag.setUser(task.getUser());
            tagRepository.save(newTag);
        }

        tags.add(newTag);
        task.setTags(tags);
        taskRepository.save(task);
    }*/

    /*public List<TaskDTO> getTasksByTags(List<String> tags, UUID userId) {
        User u = userService.getUserById(userId);
        if (u != null) {
            List<Tag> taskTags = tagRepository.findAllByUserAndNameIn(u, tags);
            List<Task> tasks = taskRepository.getTasksByUser(u);
            ArrayList<Task> filtered = new ArrayList<>();

            // Do this in SQL not in java
            // refactor pls
            for (Task t : tasks) {
                boolean contains = false;
                int i = 0;
                while (i < taskTags.size()) {
                    contains = t.getTags().contains(taskTags.get(i));
                    if (!contains) break;
                    i++;
                }
                if (contains) filtered.add(t);
            }
            return filtered.stream().map(TaskDTO::new).toList();
        }
        else throw new RuntimeException();
    }*/

    public TaskDTO getTaskById(UUID id, UUID userId) {
        User u = userService.getUserById(userId);
        if (u != null) {
            Optional<Task> optTask = taskRepository.findById(id);
            if (optTask.isPresent()) {
                if (optTask.get().getUser().getId() == userId)
                    return new TaskDTO(optTask.get());
                else throw new RuntimeException();
            }
            else throw new RuntimeException();
        }
        else throw new RuntimeException();
    }
}
