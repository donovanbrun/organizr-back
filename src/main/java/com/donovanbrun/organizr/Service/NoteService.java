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

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    public List<Note> getNotesByUsername(String userId) {
        return this.noteRepository.findNotesByUserId(userId);
    }

    public void createNote(String username, String name, String content) throws Exception {
        // TODO check if note already exist, and check params conformity
        // TODO use exception
        this.noteRepository.save(new Note(username, name, content));
    }

    public void saveNote(Note note) {

        Optional<Note> optionalNote = this.noteRepository.findById(note.getId());
        if (optionalNote.isPresent()) {
            Note n = optionalNote.get();
            n.setName(note.getName());
            n.setContent(note.getContent());
            this.noteRepository.save(n);
        }
        else throw new RuntimeException("Note doesn't exist");
    }

    public Optional<Note> getNotesById(UUID id) {
        return this.noteRepository.findById(id);
    }

    public void deleteNote(UUID noteId) {
        this.noteRepository.deleteById(noteId);
    }
}
