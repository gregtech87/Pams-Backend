package com.example.pamsbackend.fileUpAndDownload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileDownloadUtil {
    private Path foundFile;

    public ResponseEntity<?> outgoingFileHandler(String fileCode, String username, String galleryName) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();
        Resource resource = null;

        Path downloadPath = null;
        if (galleryName == null){
            downloadPath = Paths.get("User-Files/" + username);
        } else {
            downloadPath = Paths.get("User-Files/" + username + "/" + galleryName);
        }

        try {
            resource = downloadUtil.getFileAsResource(fileCode, username, downloadPath);
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

    public Resource getFileAsResource(String fileCode, String username, Path downloadPath) throws IOException {
//        Path dirPath = Paths.get("User-Files/" + username);

        Files.list(downloadPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
//                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }
}