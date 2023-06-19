package com.donovanbrun.organizr.Repository;

import com.donovanbrun.organizr.Entity.Note;
import com.donovanbrun.organizr.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {

    List<Note> findNotesByUser(User user);
}