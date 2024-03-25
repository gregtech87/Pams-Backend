package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.ItemService;
import com.example.pamsbackend.dao.UserService;
import com.example.pamsbackend.entity.Item;
import com.example.pamsbackend.entity.Note;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.repositorys.ItemRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserService userService, MongoTemplate mongoTemplate) {
        this.itemRepository = itemRepository;
        this.userService = userService;
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
    public Item registerNote(Item newItem) {
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
    public Item saveNote(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item editItem(Item item) {
        return null;
    }

    @Override
    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }
}
