package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.ItemService;
import com.example.pamsbackend.dao.PersonalFileService;
import com.example.pamsbackend.dao.UserService;
import com.example.pamsbackend.entity.Item;
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
    private final ItemService itemService;
    private final MongoTemplate mongoTemplate;
    private boolean correctOwner;
    private long fileSize;

    @Autowired
    public PersonalFileServiceImpl(PersonalFileRepository personalFileRepository, UserService userService, ItemService itemService, MongoTemplate mongoTemplate) {
        this.personalFileRepository = personalFileRepository;
        this.userService = userService;
        this.itemService = itemService;
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
        System.err.println("jsonMap = " + jsonMap.get("userId"));
        boolean fileFound = false;
        correctOwner = false;
        String fileId = jsonMap.get("fileId").toString();
        String fileIdentifier = jsonMap.get("fileIdentifier").toString();
        String fileName = jsonMap.get("fileName").toString();
        String userId = jsonMap.get("userId").toString();
        Item item = null;

        if (jsonMap.get("itemId") != null){
            Optional<Item> dbItem = itemService.findById(jsonMap.get("itemId").toString());
            if (dbItem.isPresent()){
                item = dbItem.get();
            }
        }
        System.err.println("item = " + item);


        PersonalFile personalFile;
        User user = new User();
        Path directoryPath = null;


        Optional<PersonalFile> dbPersonalFile = personalFileRepository.findById(fileId);
        if (dbPersonalFile.isPresent()) {
            personalFile = dbPersonalFile.get();
            fileSize = personalFile.getSize();
            System.out.println("dbPersonalFile = " + dbPersonalFile);
            fileFound = true;
        } else personalFile = null;

        Optional<User> dbUser = userService.findUserById(userId);
        if (dbUser.isPresent() && fileFound && item == null) {
            user = dbUser.get();
            System.out.println("user = " + user.getUsername());
            user.getPersonalFiles().forEach(id -> {
                if (id.equals(personalFile.getId())) {
                    correctOwner = true;
                }
            });
            directoryPath = Paths.get("User-Files/" + user.getUsername());
        }

        if (dbUser.isPresent() && fileFound && item != null) {
            User u = dbUser.get();
            item.getAdditionalPictureIds().forEach(id -> {
                if (id.equals(personalFile.getId())) {
                    correctOwner = true;
                }
            });
            directoryPath = Paths.get("User-Files/" + u.getUsername() + "/" + item.getTitle());
        }

        System.out.println("fileFound = " + fileFound);
        System.out.println("correctOwner = " + correctOwner);
        System.out.println("fileSize = " + fileSize);

        if (dbUser.isPresent() && fileFound && correctOwner) {
            user=dbUser.get();
            System.err.println("********** user = " + user);

            Path filePath = directoryPath.resolve(fileIdentifier + "-" + fileName);
            System.err.println("filePath = " + filePath);
            Files.deleteIfExists(filePath);
            System.err.println("filePath2 = " + Files.notExists(filePath));
            System.err.println("item = " + item);
            if (item == null){
                user.getPersonalFiles().remove(fileId);
                user.setUsedStorage(user.getUsedStorage()-fileSize);
                userService.save(user);
            } else {
                System.err.println("else");
                item.getAdditionalPictureIds().remove(fileId);
                System.err.println("user = " + user);
                user.setUsedStorage(user.getUsedStorage()-fileSize);
                System.err.println("user = " + user);
                itemService.saveItem(item);
                userService.save(user);
            }

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

    @Override
    public void deleteFileEntryOnly(String id) {
         personalFileRepository.deleteById(id);
    }
}
