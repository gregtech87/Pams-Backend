package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.PersonalFileService;
import com.example.pamsbackend.dao.UserService;
import com.example.pamsbackend.entity.PersonalFile;
import com.example.pamsbackend.repositorys.PersonalFileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonalFileServiceImpl implements PersonalFileService {

    private final PersonalFileRepository personalFileRepository;
    private final UserService userService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PersonalFileServiceImpl(PersonalFileRepository personalFileRepository, UserService userService, MongoTemplate mongoTemplate) {
        this.personalFileRepository = personalFileRepository;
        this.userService = userService;
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
    public String deleteFiles(String id, String identifier) {
    userService.getUserById(id);
    return "uggaBugga";
    }
@Override
public String deleteFiles2(MultipartFile multipartFile) {
    System.out.println("PersonalFileServiceImpl.deleteFiles2");
        System.out.println(multipartFile);
    return "uggaBugga2";
}

    @Override
    public String deleteFiles3(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        System.err.println("jsonMap = " + jsonMap);
        System.err.println("jsonMap = " + jsonMap.get("id"));
        return "uggaBugga3";
    }
}
