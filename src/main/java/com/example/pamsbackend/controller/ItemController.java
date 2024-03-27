package com.example.pamsbackend.controller;

import com.example.pamsbackend.dao.ItemService;
import com.example.pamsbackend.dao.PersonalFileService;
import com.example.pamsbackend.entity.Item;
import com.example.pamsbackend.fileUpAndDownload.FileDownloadUtil;
import com.example.pamsbackend.fileUpAndDownload.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://pam-gui.gregtech.duckdns.org")
@RequestMapping("/api/v1")
public class ItemController {

    private final FileUploadUtil fileUploadUtil;
    private final ItemService itemService;
    private final FileDownloadUtil fileDownloadUtil;
    private final PersonalFileService personalFileService;

    @Autowired
    public ItemController(FileUploadUtil fileUploadUtil, ItemService itemService, FileDownloadUtil fileDownloadUtil, PersonalFileService personalFileService) {
        this.fileUploadUtil = fileUploadUtil;
        this.itemService = itemService;
        this.fileDownloadUtil = fileDownloadUtil;
        this.personalFileService = personalFileService;
    }

    @GetMapping("/item")
    public List<Item> getAll() {
        System.out.println("ItemController.getAll");
        System.out.println("item = " + itemService.findAllItems());
        return itemService.findAllItems();
    }

    @GetMapping("/item/{id}")
    public Optional<Item> getItem(@PathVariable String id) {
        System.out.println("ItemController.getItem");
        System.out.println("items = " + id);
        return itemService.findById(id);
    }
    @GetMapping("/items/{ids}")
    public List<Item> getItems(@PathVariable List<String> ids) {
        System.out.println("ItemController.getItems");
        System.out.println("items = " + ids);
        return itemService.findItemsByIds(ids);
    }

    @PostMapping("/item")
    public Item regItem(@RequestBody Item item) {
        System.err.println("ItemController.regItem");
        System.err.println("item = " + item);
        return itemService.registerItem(item);

    }

//    @GetMapping("/downloadFile/{fileCode}/{username}")
//    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode, @PathVariable("username") String username) {
//        return fileDownloadUtil.outgoingFileHandler(fileCode, username);
//    }
//
//    @GetMapping("/file/{ids}")
//    public List<PersonalFile> getFiles(@PathVariable List<String> ids) {
//        System.out.println("FileController.getNots");
//        System.out.println(ids);
//        return personalFileService.findFilesByIds(ids);
//    }
//
//    @DeleteMapping("/file/{json}")
//    public String deleteFile(@PathVariable String json) throws IOException {
//        System.err.println("FileController.deleteFile3");
//        System.out.println(json);
//        return personalFileService.deleteFile(json);
//    }
}