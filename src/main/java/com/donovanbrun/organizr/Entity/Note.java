package com.donovanbrun.organizr.Entity;

import com.donovanbrun.organizr.dto.NoteDTO;
import com.sun.istack.NotNull;
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
    @NotNull
    private UUID id;

    @ManyToOne
    private User user;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String content;

    public Note(NoteDTO noteDTO) {
        this.id = noteDTO.getId();
        this.name = noteDTO.getName();
        this.content = noteDTO.getContent();
    }
}
