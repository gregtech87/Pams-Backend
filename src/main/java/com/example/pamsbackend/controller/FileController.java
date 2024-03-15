package com.example.pamsbackend.controller;

import com.example.pamsbackend.PdfUserInfo.PdfUser;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.fileUpAndDownload.FileDownloadUtil;
import com.example.pamsbackend.fileUpAndDownload.FileUploadUtil;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class FileController {

    private final FileUploadUtil fileUploadUtil;
    private final FileDownloadUtil fileDownloadUtil;

    @Autowired
    public FileController(FileUploadUtil fileUploadUtil, FileDownloadUtil fileDownloadUtil) {
        this.fileUploadUtil = fileUploadUtil;
        this.fileDownloadUtil = fileDownloadUtil;
    }

    //TODO link file with user.

    @PostMapping("/uploadFile")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("username") String username) throws IOException {
        System.out.println("FileController.uploadFile");
        System.out.println("multipartFile = " + multipartFile);
        System.out.println("username = " + username);
        return fileUploadUtil.incomingFileHandler(multipartFile, username);

    }

    @GetMapping("/userPdf/{userId}")
    public PdfUser generateUserPdf(@PathVariable("userId") String userId) throws JRException, IOException {
        User user = fileUploadUtil.makeUserPdf(userId);
        return user.getPdfUser();
    }

    @GetMapping("/downloadFile/{fileCode}/{username}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode, @PathVariable("username") String username) {
        return fileDownloadUtil.outgoingFileHandler(fileCode, username);
    }


}