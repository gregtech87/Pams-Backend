package com.example.pamsbackend.dao;

import com.example.pamsbackend.entity.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    List<Note> getAllNotes();
    Optional<Note> findById(String id);
    List<Note> findNotesByIds(List<String> noteIds);
    Note saveNote(Note newNote);
    Note editNote(Note note);
    void deleteNote(String id);
}
