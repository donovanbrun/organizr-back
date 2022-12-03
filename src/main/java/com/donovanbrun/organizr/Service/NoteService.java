package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.Note;
import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Repository.NoteRepository;
import com.donovanbrun.organizr.dto.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {

    private NoteRepository noteRepository;
    private UserService userService;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public List<NoteDTO> getNotesByUser(UUID userId) {
        User u = userService.getUserById(userId);
        if (u != null) {
            List<Note> notes = this.noteRepository.findNotesByUser(u);
            ArrayList<NoteDTO> retNotes = new ArrayList<>(notes.size());

            for (Note note : notes) {
                retNotes.add(new NoteDTO(note));
            }
            return retNotes;
        }
        else throw new RuntimeException();
    }

    public void createNote(UUID userId, NoteDTO noteDTO) throws Exception {
        User u = userService.getUserById(userId);
        if (u != null) {
            Note note = new Note(noteDTO);
            note.setUser(u);
            this.noteRepository.save(note);
        }
        else throw new RuntimeException();
    }

    public void saveNote(UUID userId, NoteDTO noteDTO) {
        User u = userService.getUserById(userId);
        if (u != null && u.getId().equals(noteDTO.getUserId())) {
            Note note = new Note(noteDTO);
            note.setUser(u);
            this.noteRepository.save(note);
        }
        else throw new RuntimeException();
    }

    public NoteDTO getNoteById(UUID userId, UUID noteId) {
        User u = userService.getUserById(userId);
        if (u != null) {
            Optional<Note> optionalNote = this.noteRepository.findById(noteId);
            if(optionalNote.isPresent() && optionalNote.get().getUser().getId().equals(userId))
                return new NoteDTO(optionalNote.get());
            else throw new RuntimeException();
        }
        else throw new RuntimeException();
    }

    public void deleteNote(UUID userId, UUID noteId) {
        User u = userService.getUserById(userId);
        Optional<Note> n = noteRepository.findById(noteId);
        if (u != null && n.isPresent() && n.get().getUser().getId().equals(userId)) {
            this.noteRepository.deleteById(noteId);
        }
        else throw new RuntimeException();
    }
}
