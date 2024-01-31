package com.example.pamsbackend.dao;

import com.example.pamsbackend.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository  extends MongoRepository<Item, String> {

}
