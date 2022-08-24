package com.donovanbrun.organizr.Repository;

import com.donovanbrun.organizr.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findNotesByUserId(String userId);
}

