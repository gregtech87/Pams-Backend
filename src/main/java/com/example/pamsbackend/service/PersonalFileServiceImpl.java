package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.PersonalFileService;
import com.example.pamsbackend.dao.UserService;
import com.example.pamsbackend.entity.PersonalFile;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.repositorys.PersonalFileRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonalFileServiceImpl implements PersonalFileService {

    private final PersonalFileRepository personalFileRepository;
    private final UserService userService;
    private final MongoTemplate mongoTemplate;
    private boolean correctOwner;
    private long fileSize;

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
    public String deleteFile(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
        });
        System.err.println("jsonMap = " + jsonMap);
        System.err.println("jsonMap = " + jsonMap.get("id"));
        boolean fileFound = false;
        correctOwner = false;
        String fileId = jsonMap.get("fileId").toString();
        String fileIdentifier = jsonMap.get("fileIdentifier").toString();
        String fileName = jsonMap.get("fileName").toString();
        String userId = jsonMap.get("userId").toString();

        PersonalFile personalFile;
        User user = new User();

        Optional<PersonalFile> dbPersonalFile = personalFileRepository.findById(fileId);
        if (dbPersonalFile.isPresent()) {
            personalFile = dbPersonalFile.get();
            fileSize = personalFile.getSize();
            System.out.println("dbPersonalFile = " + dbPersonalFile);
            fileFound = true;
            Optional<User> dbUser = userService.findUserById(jsonMap.get("userId").toString());
            if (dbUser.isPresent()) {
                user = dbUser.get();
                System.out.println("user = " + user.getUsername());
                user.getPersonalFiles().forEach(id -> {
                    if (id.equals(personalFile.getId())) {
                        correctOwner = true;
                    }
                });
            }
        }
        System.out.println("fileFound = " + fileFound);
        System.out.println("correctOwner = " + correctOwner);
        System.out.println("fileSize = " + fileSize);
        if (fileFound && correctOwner) {
            Path uploadPath = Paths.get("User-Files/" + user.getUsername());
            Path filePath = uploadPath.resolve(fileIdentifier + "-" + fileName);
            Files.deleteIfExists(filePath);
            user.getPersonalFiles().remove(fileId);
            user.setUsedStorage(user.getUsedStorage()-fileSize);
            userService.save(user);
            personalFileRepository.deleteById(fileId);
            if (Files.notExists(filePath)) {
                return "File deleted sucessfully!";
            } else {
                return "File did not get deleted for some reason, contact support.";
            }
        } else {
            System.out.println("Incorrect parameter, File found: " + fileFound + ", Correct owner: " + correctOwner);
            return "Incorrect parameter, File found: " + fileFound + ", Correct owner: " + correctOwner;
        }
    }
}
