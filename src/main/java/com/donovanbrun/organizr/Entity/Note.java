package com.donovanbrun.organizr.Entity;

import com.donovanbrun.organizr.dto.NoteDTO;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Note")
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Note {

    @Id
    @NotNull
    private UUID id;

    @ManyToOne
    private User user;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String content;

    private Date creationDate;
    private Date updateDate;

    public Note(NoteDTO noteDTO) {
        this.id = noteDTO.getId();
        this.name = noteDTO.getName();
        this.content = noteDTO.getContent();
        this.creationDate = noteDTO.getCreationDate();
        this.updateDate = noteDTO.getUpdateDate();
    }

    public Note() {
        this.id = UUID.randomUUID();
        this.name = "";
        this.content = "";
        this.creationDate = new Date();
        this.updateDate = new Date();
    }
}
