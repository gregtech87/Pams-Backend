package com.example.pamsbackend.controller;

import com.example.pamsbackend.PdfUserInfo.PdfUser;
import com.example.pamsbackend.dao.PersonalFileService;
import com.example.pamsbackend.entity.PersonalFile;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.fileUpAndDownload.FileDownloadUtil;
import com.example.pamsbackend.fileUpAndDownload.FileUploadUtil;
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

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("username") String username) throws IOException {
        return fileUploadUtil.incomingFileHandler(multipartFile, username, null);
    }

    @PostMapping("/uploadToGallery")
    public ResponseEntity<?> uploadToGallery(@RequestParam("file") MultipartFile multipartFile, @RequestParam("username") String username, @RequestParam("itemId") String itemId) throws IOException {
        return fileUploadUtil.incomingFileHandler(multipartFile, username, itemId);
    }

    @GetMapping("/userPdf/{userId}")
    public PdfUser generateUserPdf(@PathVariable("userId") String userId) throws JRException, IOException {
        User user = fileUploadUtil.makeUserPdf(userId);
        return user.getPdfUser();
    }

    @GetMapping("/downloadFile/{fileCode}/{username}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode, @PathVariable("username") String username) {
        return fileDownloadUtil.outgoingFileHandler(fileCode, username, null);
    }

    @GetMapping("/downloadFile/{fileCode}/{username}/{galleryName}")
    public ResponseEntity<?> downloadGalleryFile(@PathVariable("fileCode") String fileCode, @PathVariable("username") String username, @PathVariable String galleryName) {
        return fileDownloadUtil.outgoingFileHandler(fileCode, username, galleryName);
    }

    @GetMapping("/file/{ids}")
    public List<PersonalFile> getFiles(@PathVariable List<String> ids) {
        return personalFileService.findFilesByIds(ids);
    }

    @DeleteMapping("/file/{json}")
    public String deleteFile(@PathVariable String json) throws IOException {
        return personalFileService.deleteFile(json);
    }
}