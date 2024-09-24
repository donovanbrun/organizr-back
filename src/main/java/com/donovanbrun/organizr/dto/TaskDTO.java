package com.donovanbrun.organizr.dto;

import com.donovanbrun.organizr.Entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class TaskDTO {

    private UUID id;
    private UUID ownerId;
    private UUID workspaceId;
    private String title;
    private Date deadline;
    private String description;
    private String status;
    private Date creationDate;
    private Date updateDate;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.ownerId = task.getOwner().getId();
        this.workspaceId = task.getWorkspace().getId();
        this.title = task.getTitle();
        this.deadline = task.getDeadline();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.creationDate = task.getCreationDate();
        this.updateDate = task.getUpdateDate();
    }
}
