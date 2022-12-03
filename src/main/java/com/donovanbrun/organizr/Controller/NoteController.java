package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Entity.Note;
import com.donovanbrun.organizr.Service.NoteService;
import com.donovanbrun.organizr.dto.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/note")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping(path = "status")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public void getStatus() {}

    @GetMapping()
    public List<NoteDTO> getNote(@RequestHeader UUID userId) {
        return noteService.getNotesByUser(userId);
    }

    @GetMapping("{id}")
    public NoteDTO getNoteById(@PathVariable UUID id, @RequestHeader UUID userId) {
        return noteService.getNoteById(userId, id);
    }

    @PostMapping()
    public void createNote(@RequestBody NoteDTO noteDTO, @RequestHeader UUID userId) {
        try {
            this.noteService.createNote(userId, noteDTO);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.CREATED);
    }

    @PutMapping()
    public void saveNote(@RequestBody NoteDTO noteDTO, @RequestHeader UUID userId) {
        try {
            this.noteService.saveNote(userId, noteDTO);
        }
        catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.OK);
    }

    @DeleteMapping("{noteId}")
    public void deleteNote(@PathVariable UUID noteId, @RequestHeader UUID userId) {
        this.noteService.deleteNote(userId, noteId);
    }
}
