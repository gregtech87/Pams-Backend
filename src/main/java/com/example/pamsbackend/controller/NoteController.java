package com.example.pamsbackend.controller;

import com.example.pamsbackend.entity.Note;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.service.NoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class NoteController {

    private final NoteServiceImpl noteServiceImpl;

    @Autowired
    public NoteController(NoteServiceImpl noteServiceImpl) {
        this.noteServiceImpl = noteServiceImpl;
    }

    @GetMapping("/notes")
    public List<Note> getAll() {
        return noteServiceImpl.getAllNotes();
    }

//    @GetMapping("/note/{id}")
//    public Optional<Note> getNoteById(@PathVariable String id) {
//        System.out.println(id);
//        System.out.println(noteServiceImpl.findById(id));
//        return noteServiceImpl.findById(id);
//    }

    @GetMapping("/note/{ids}")
    public List<Note> getNots(@PathVariable List<String> ids) {
        System.out.println("NoteController.getNots");
        System.out.println(ids);
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
