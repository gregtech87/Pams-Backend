package com.example.pamsbackend.controller;

import com.example.pamsbackend.PdfUserInfo.PdfUser;
import com.example.pamsbackend.dao.PersonalFileService;
import com.example.pamsbackend.entity.PersonalFile;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.fileUpAndDownload.FileDownloadUtil;
import com.example.pamsbackend.fileUpAndDownload.FileUploadUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://pam-gui.gregtech.duckdns.org")
@RequestMapping("/api/v1")
public class FileController {

    private final FileUploadUtil fileUploadUtil;
    private final FileDownloadUtil fileDownloadUtil;
    private final PersonalFileService personalFileService;

    @Autowired
    public FileController(FileUploadUtil fileUploadUtil, FileDownloadUtil fileDownloadUtil, PersonalFileService personalFileService) {
        this.fileUploadUtil = fileUploadUtil;
        this.fileDownloadUtil = fileDownloadUtil;
        this.personalFileService = personalFileService;
    }

    //TODO link file with user.

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("username") String username) throws IOException {
        System.out.println("FileController.uploadFile");
        System.out.println("multipartFile = " + multipartFile);
        System.out.println("multipartFile = " + multipartFile.getOriginalFilename());
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

    @GetMapping("/file/{ids}")
    public List<PersonalFile> getFiles(@PathVariable List<String> ids) {
        System.out.println("FileController.getNots");
        System.out.println(ids);
        return personalFileService.findFilesByIds(ids);
    }

    @DeleteMapping("/file/{json}")
    public String deleteFile3(@PathVariable String json) throws JsonProcessingException {
        System.err.println("FileController.deleteFile3");
        System.out.println(json);
        return personalFileService.deleteFiles3(json);
    }
    @DeleteMapping("/file/{id}/{identifier}")
    public String deleteFile(@PathVariable String id, @PathVariable String identifier) {
        System.err.println("FileController.deleteFile");
        System.out.println(id);
        System.out.println(identifier);
        return personalFileService.deleteFiles(id, identifier);
    }

//    @DeleteMapping("/file}")
//    public String deleteFile2(@RequestParam("file") MultipartFile multipartFile) {
//        System.out.println("FileController.deleteFile2");
//        System.out.println("multipartFile = " + multipartFile);
//        System.out.println("multipartFile = " + multipartFile.getOriginalFilename());
//        System.out.println("multipartFile = " + multipartFile.getResource());
//        System.out.println("multipartFile = " + multipartFile.getSize());
//        return personalFileService.deleteFiles2(multipartFile);
//    }
}