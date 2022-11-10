package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.Note;
import com.donovanbrun.organizr.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotesByUsername(String userId) {
        return this.noteRepository.findNotesByUserId(userId);
    }

    public void createNote(String username, String name, String content) throws Exception {
        this.noteRepository.save(new Note(username, name, content));
    }

    public void saveNote(Note note) {
        this.noteRepository.save(note);
    }

    public Optional<Note> getNotesById(UUID id) {
        return this.noteRepository.findById(id);
    }

    public void deleteNote(UUID noteId) {
        this.noteRepository.deleteById(noteId);
    }
}
