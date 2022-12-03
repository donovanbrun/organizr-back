package com.donovanbrun.organizr.Entity;

import com.donovanbrun.organizr.dto.TaskDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Task")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;
    private String name;
    private Date deadline;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String status;

    private Date creationDate;
    private Date modificationDate;

    @ManyToMany
    private List<Tag> tags;

    public Task(TaskDTO taskDTO) {
        this.id = taskDTO.getId();
        this.name = taskDTO.getName();
        this.deadline = taskDTO.getDeadline();
        this.description = taskDTO.getDescription();
        this.status = taskDTO.getStatus();
        this.creationDate = taskDTO.getCreationDate();
        this.modificationDate = taskDTO.getModificationDate();

        this.tags = new ArrayList<>();
    }
}
