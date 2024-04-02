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
@CrossOrigin(origins = "https://pam-gui.gregtech.duckdns.org")
@RequestMapping("/api/v1")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item")
    public List<Item> getAll() {
        return itemService.findAllItems();
    }

    @GetMapping("/item/{id}")
    public Optional<Item> getItem(@PathVariable String id) {
        return itemService.findById(id);
    }

    @GetMapping("/items/{ids}")
    public List<Item> getItems(@PathVariable List<String> ids) {
        return itemService.findItemsByIds(ids);
    }

    @PostMapping("/item")
    public Item regItem(@RequestBody Item item) {
        return itemService.registerItem(item);
    }

    @PutMapping("/item")
    public Item updateItem(@RequestBody Item item) {
        return itemService.editItem(item);
    }

    @DeleteMapping("/item/{id}")
    public String deleteItem(@PathVariable String id) throws IOException {
        return itemService.deleteItem(id);
    }
}