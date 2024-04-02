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
@CrossOrigin(origins = "https://pam-gui.gregtech.duckdns.org")
@RequestMapping("/api/v1")
public class NoteController {

    private final NoteServiceImpl noteServiceImpl;

    @Autowired
    public NoteController(NoteServiceImpl noteServiceImpl) {
        this.noteServiceImpl = noteServiceImpl;
    }

    @GetMapping("/note/{ids}")
    public List<Note> getNots(@PathVariable List<String> ids) {
        return noteServiceImpl.findNotesByIds(ids);
    }

    @PostMapping("/note")
    public Note newNote(@RequestBody Note note) {
        return noteServiceImpl.saveNote(note);
    }

    @PutMapping("/note")
    public Object updateNote(@RequestBody Note note) {
        return noteServiceImpl.editNote(note);
    }

    @DeleteMapping("/note/{id}")
    public void deleteNote(@PathVariable String id) {
        noteServiceImpl.deleteNote(id);
    }
}
