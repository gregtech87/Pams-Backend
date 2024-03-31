package com.example.pamsbackend.repositorys;

import com.example.pamsbackend.entity.PersonalFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalFileRepository extends MongoRepository<PersonalFile, String> {
}

