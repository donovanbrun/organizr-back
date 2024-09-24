package com.donovanbrun.organizr.Entity;

import com.donovanbrun.organizr.dto.TaskDTO;
import lombok.*;
import jakarta.persistence.*;
import java.util.Date;
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
    @JoinColumn(nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Workspace workspace;

    @ManyToOne
    private Project project;

    private String title;
    private Date deadline;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String status;

    @Column(nullable = false)
    private Date creationDate;
    @Column(nullable = false)
    private Date updateDate;

    public Task(TaskDTO taskDTO) {
        this.id = taskDTO.getId();
        this.title = taskDTO.getTitle();
        this.deadline = taskDTO.getDeadline();
        this.description = taskDTO.getDescription();
        this.status = taskDTO.getStatus();
        this.creationDate = taskDTO.getCreationDate();
        this.updateDate = taskDTO.getUpdateDate();
    }
}
