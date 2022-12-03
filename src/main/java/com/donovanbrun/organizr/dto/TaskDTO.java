package com.donovanbrun.organizr.dto;
import com.donovanbrun.organizr.Entity.Tag;
import com.donovanbrun.organizr.Entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class TaskDTO {

    private UUID id;
    private UUID userId;
    private String name;
    private Date deadline;
    private String description;
    private String status;
    private Date creationDate;
    private Date modificationDate;
    private List<String> tags;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.userId = task.getUser().getId();
        this.name = task.getName();
        this.deadline = task.getDeadline();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.creationDate = task.getCreationDate();
        this.modificationDate = task.getModificationDate();
        this.tags = new ArrayList<>();

        for (Tag tag : task.getTags()) {
            tags.add(tag.getName());
        }
    }
}
