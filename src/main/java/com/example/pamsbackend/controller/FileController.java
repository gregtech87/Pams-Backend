package com.example.pamsbackend.controller;

import com.example.pamsbackend.dao.UserService;

import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.fileUpAndDownload.FileUploadUtil;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private static Path foundFile;
    private UserService userService;
    private FileUploadUtil fileUploadUtil;

    @Autowired
    public FileController(UserService userService, FileUploadUtil fileUploadUtil) {
        this.userService = userService;
        this.fileUploadUtil = fileUploadUtil;
    }

    //TODO link file with user.

    @PostMapping("/uploadFile")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("username") String username) throws IOException {
        return fileUploadUtil.incomingFileHandler(multipartFile, username);

    }

    @GetMapping("/downloadFile/{fileCode}/{username}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode, @PathVariable("username") String username) {
        return fileUploadUtil.outgoingFileHandler(fileCode, username);
    }

    @GetMapping("/userPdf/{userId}")
    public User generateUserPdf(@PathVariable("userId") String userId) throws JRException, IOException {
        return fileUploadUtil.makeUserPdf(userId);
    }
}