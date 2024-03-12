package com.example.pamsbackend.controller;

import com.example.pamsbackend.entity.FileUploadResponse;
import com.example.pamsbackend.fileUpAndDownload.FileDownloadUtil;
import com.example.pamsbackend.fileUpAndDownload.FileUploadUtil;
import com.example.pamsbackend.fileUpAndDownload.InstpectFolder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private static Path foundFile;
//    private boolean fileNameExists = false;
    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile multipartFile, @RequestParam("username") String username)
            throws IOException {

        System.out.println(multipartFile.toString());
        System.out.println(username);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        long size = multipartFile.getSize();


        if(size == 0){
            FileUploadResponse response = new FileUploadResponse();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            boolean fileNameExists = new InstpectFolder().inspect(fileName, username);

            if(!fileNameExists) {
                String fileCode = FileUploadUtil.saveFile(fileName, multipartFile, username);

                FileUploadResponse response = new FileUploadResponse();
                response.setFileName(fileName);
                response.setSize(size);
                response.setIdentifyer(fileCode);
                System.out.println(multipartFile.getSize());

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                FileUploadResponse response = new FileUploadResponse();
                response.setFileName(fileName);
                response.setSize(size);
                response.setFileAlreadyExists(true);

                return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
            }

        }
    }

    @GetMapping("/downloadFile/{fileCode}/{username}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode, @PathVariable("username") String username) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();
        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(fileCode, username);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }


/*    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile multipartFile)
            throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }*/
}