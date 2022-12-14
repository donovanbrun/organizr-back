package com.donovanbrun.organizr.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Note")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Note {

    @Id
    @GeneratedValue()
    private UUID id;

    private String userId;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String content;

    public Note(String userId, String name, String content) {
        this.userId = userId;
        this.name = name;
        this.content = content;
    }
}
