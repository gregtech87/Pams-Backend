package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.PersonalFileService;
import com.example.pamsbackend.entity.Note;
import com.example.pamsbackend.entity.PersonalFile;
import com.example.pamsbackend.repositorys.PersonalFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalFileServiceImpl implements PersonalFileService {

    private final PersonalFileRepository personalFileRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PersonalFileServiceImpl(PersonalFileRepository personalFileRepository, MongoTemplate mongoTemplate) {
        this.personalFileRepository = personalFileRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<PersonalFile> getAllFiles() {
        return null;
    }

    @Override
    public Optional<PersonalFile> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<PersonalFile> findFilesByIds(List<String> fileIds) {
        Query query = new Query(Criteria.where("_id").in(fileIds));
        return mongoTemplate.find(query, PersonalFile.class);
    }

    @Override
    public PersonalFile saveFile(PersonalFile file) {
        return personalFileRepository.save(file);
    }

    @Override
    public PersonalFile editFiles(PersonalFile file) {
        return null;
    }

    @Override
    public void deleteFiles(String id) {

    }
}
