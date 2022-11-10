package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Entity.Note;
import com.donovanbrun.organizr.Entity.ReceivedNote;
import com.donovanbrun.organizr.Service.NoteService;
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

    @GetMapping("user/{username}")
    public List<Note> getNote(@PathVariable String username) {
        return noteService.getNotesByUsername(username);
    }

    @GetMapping("{id}")
    public Optional<Note> getNoteById(@PathVariable UUID id) {
        return noteService.getNotesById(id);
    }

    @PostMapping()
    public void createNote(@RequestBody ReceivedNote body) {
        try {
            this.noteService.createNote(body.getUsername(), body.getName(), body.getContent());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.CREATED);
    }

    @PutMapping()
    public void saveNote(@RequestBody Note note) {
        System.out.println(note);
        try {
            this.noteService.saveNote(note);
        }
        catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.OK);
    }

    @DeleteMapping("{noteId}")
    public void deleteNote(@PathVariable UUID noteId) {
        this.noteService.deleteNote(noteId);
    }
}
