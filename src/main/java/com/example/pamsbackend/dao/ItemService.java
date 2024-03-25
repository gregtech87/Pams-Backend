package com.example.pamsbackend.dao;

import com.example.pamsbackend.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> findAllItems();
    Optional<Item> findById(String id);
    List<Item> findItemsByIds(List<String> itemIds);
    Item registerNote(Item newItem);
    Item saveNote(Item item);
    Item editItem(Item item);
    void deleteItem(String id);
}
