package com.example.pamsbackend.repositorys;

import com.example.pamsbackend.entity.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
}
