/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.controller;

import com.example.pamsbackend.entity.Note;
import com.example.pamsbackend.service.NoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pam-gui.gregtech.org")
public class NoteController {

    private final NoteServiceImpl noteServiceImpl;

    @Autowired
    public NoteController(NoteServiceImpl noteServiceImpl) {
        this.noteServiceImpl = noteServiceImpl;
    }

    @GetMapping("/v1/note/{ids}")
    public List<Note> getNots(@PathVariable List<String> ids) {
        return noteServiceImpl.findNotesByIds(ids);
    }

    @PostMapping("/v1/note")
    public Note newNote(@RequestBody Note note) {
        return noteServiceImpl.saveNote(note);
    }

    @PutMapping("/v1/note")
    public Object updateNote(@RequestBody Note note) {
        return noteServiceImpl.editNote(note);
    }

    @DeleteMapping("/v1/note/{id}")
    public void deleteNote(@PathVariable String id) {
        noteServiceImpl.deleteNote(id);
    }
}
