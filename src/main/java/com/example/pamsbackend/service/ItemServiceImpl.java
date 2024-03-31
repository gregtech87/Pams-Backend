package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.ItemService;
import com.example.pamsbackend.dao.PersonalFileService;
import com.example.pamsbackend.dao.UserService;
import com.example.pamsbackend.entity.Item;
import com.example.pamsbackend.entity.PersonalFile;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.repositorys.ItemRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final PersonalFileService personalFileService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserService userService, @Lazy PersonalFileService personalFileService, MongoTemplate mongoTemplate) {
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.personalFileService = personalFileService;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Optional<Item> findById(String id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> findItemsByIds(List<String> itemIds) {
        Query query = new Query(Criteria.where("_id").in(itemIds));
        return mongoTemplate.find(query, Item.class);
    }

    @Override
    public Item registerItem(Item newItem) {
        newItem.setId(new ObjectId().toString());
        System.out.println("ItemServiceImpl.registerNote");
        System.out.println("newItem = " + newItem);
        Optional<User> dbUser = userService.findUserById(newItem.getOwner());
        if (dbUser.isPresent()) {
            User user = dbUser.get();
            List<String> items = user.getItems();
            items.add(newItem.getId());
            userService.save(user);
        }
        return itemRepository.save(newItem);
    }

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Item editItem(Item editedItem) {
        return itemRepository.save(editedItem);
    }

    @Override
    public String deleteItem(String id) throws IOException {
        boolean itemPictureFilesRemoved = false;
        boolean itemFolderRemoved = false;
        boolean userItemRemoved = false;
        Optional<Item> dbItem = findById(id);
        if (dbItem.isPresent()) {
            Item item = dbItem.get();
            Optional<User> dbUser = userService.findUserById(item.getOwner());
            if (dbUser.isPresent()) {
                User user = dbUser.get();
                userItemRemoved = removeItemFromUser(user, item.getId());
                itemFolderRemoved = removeFileFolder(user.getUsername(), item.getTitle());
                itemPictureFilesRemoved = removeAdditionalPictures(user, item);
            }
        }
        if (itemPictureFilesRemoved && itemFolderRemoved && userItemRemoved) {
        itemRepository.deleteById(id);
        // TODO Delete response not customized
            return "Deluttad";
        } else {
            return "POOOOOP";
        }
    }

    private boolean removeAdditionalPictures(User user, Item item) {
        boolean success = false;
        List<String> idList = item.getAdditionalPictureIds();
        if (idList.isEmpty()) {
            success = true;
        } else {
            List<PersonalFile> fileList = personalFileService.findFilesByIds(idList);
            try {
                for (PersonalFile personalFile : fileList) {
                    user.setUsedStorage(user.getUsedStorage() - personalFile.getSize());
                    personalFileService.deleteFileEntryOnly(personalFile.getId());
                }
                userService.save(user);
                success = true;
            } catch (Exception e) {
                System.err.println("ERROR DELETE FILE ENTRY IN DATABASE! From user: " + user.getId() + ", File list from Item: " + item.getId());
            }
        }
        return success;
    }

    private boolean removeFileFolder(String userId, String itemId) throws IOException {
        boolean success = false;
        Path directoryPath = Paths.get("User-Files/" + userId + "/" + itemId);

        if (Files.exists(directoryPath)) {
            Files.list(directoryPath).forEach(file -> {
                Path fullPath = null;
                try {
                    fullPath = Path.of(directoryPath + "/" + file.getFileName());
                    Files.delete(fullPath);
                    System.out.println("exists: " + Files.exists(directoryPath));
                } catch (IOException e) {
                    System.err.println("******************* ERROR DELETE FILE: " + fullPath + " ******************");
                }
            });
        }
        if (Files.exists(directoryPath )){
            try {
                Files.deleteIfExists(directoryPath);
                System.err.println("******************* DIRECTORY DELETED: " + directoryPath + " ******************");
                success = true;
            } catch (IOException e) {
                System.err.println("******************* ERROR DELETE DIRECTORY: " + directoryPath + " ******************");
            }
        } else {
            System.err.println("******************* DIRECTORY ALREADY DELETED: " + directoryPath + " ******************");
            success = true;
        }
        return success;
    }

    private boolean removeItemFromUser(User user, String itemId) {
        boolean success = false;
        int listSizeBefore = user.getItems().size();
        user.getItems().remove(itemId);
        int listSizeAfter = user.getItems().size();
        if (listSizeAfter < listSizeBefore) {
            userService.save(user);
            success = true;
        }
        return success;
    }
}
