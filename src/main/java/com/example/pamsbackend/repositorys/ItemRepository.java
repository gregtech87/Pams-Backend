package com.example.pamsbackend.repositorys;

import com.example.pamsbackend.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
}
