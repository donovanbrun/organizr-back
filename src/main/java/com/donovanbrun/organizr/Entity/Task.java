package com.donovanbrun.organizr.Entity;

import lombok.*;

import javax.persistence.*;
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
    @GeneratedValue()
    private UUID id;

    private String userId;
    private String name;
    private Date deadline;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String status;

    private Date creationDate;
    private Date modificationDate;
}
