package com.donovanbrun.organizr.dto;
import com.donovanbrun.organizr.Entity.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class NoteDTO {

    private UUID id;
    private UUID userId;
    private String name;
    private String content;

    public NoteDTO(Note note) {
        this.id = note.getId();
        this.userId = note.getUser().getId();
        this.name = note.getName();
        this.content = note.getContent();
    }
}
