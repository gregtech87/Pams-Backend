package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.NoteService;
import com.example.pamsbackend.entity.Note;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.repositorys.NoteRepository;
import com.example.pamsbackend.repositorys.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Note> getAllNotes() {
        return null;
    }

    @Override
    public Optional<Note> findById(String id) {
        return noteRepository.findById(id);
    }

    @Override
    public List<Note> findNotesByIds(List<String> noteIds) {
        Query query = new Query(Criteria.where("_id").in(noteIds));
        return mongoTemplate.find(query, Note.class);
    }

    @Override
    public Note saveNote(Note newNote) {

        newNote.setId(new ObjectId().toString());

        System.out.println("note L42"+newNote);
        Optional<User> dbUser = userRepository.findById(newNote.getAuthor());
        if (dbUser.isPresent()) {
            User user = dbUser.get();
            List<String> notes = user.getNotes();
            notes.add(newNote.getId());
            userRepository.save(user);
        }

        return noteRepository.save(newNote);
    }

    @Override
    public Note editNote(Note note) {
        return null;
    }

    @Override
    public void deleteNote(String id) {

    }
}
