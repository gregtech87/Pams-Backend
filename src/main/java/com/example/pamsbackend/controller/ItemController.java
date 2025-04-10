/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.controller;

import com.example.pamsbackend.dao.ItemService;
import com.example.pamsbackend.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://pam-gui.gregtech.org")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/v1/item")
    public List<Item> getAll() {
        return itemService.findAllItems();
    }

    @GetMapping("/v1/item/{id}")
    public Optional<Item> getItem(@PathVariable String id) {
        return itemService.findById(id);
    }

    @GetMapping("/v1/items/{ids}")
    public List<Item> getItems(@PathVariable List<String> ids) {
        return itemService.findItemsByIds(ids);
    }

    @PostMapping("/v1/item")
    public Item regItem(@RequestBody Item item) {
        return itemService.registerItem(item);
    }

    @PutMapping("/v1/item")
    public Item updateItem(@RequestBody Item item) {
        return itemService.editItem(item);
    }

    @DeleteMapping("/v1/item/{id}")
    public String deleteItem(@PathVariable String id) throws IOException {
        return itemService.deleteItem(id);
    }
}