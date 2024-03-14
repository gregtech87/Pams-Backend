package com.example.pamsbackend.fileUpAndDownload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

import com.example.pamsbackend.PdfUserInfo.PDFgenerator;
import com.example.pamsbackend.PdfUserInfo.PdfUser;
import com.example.pamsbackend.dao.UserService;
import com.example.pamsbackend.entity.User;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadUtil {

    private final UserService userService;
    private final PDFgenerator pdFgenerator;

    @Autowired
    public FileUploadUtil(UserService userService, PDFgenerator pdFgenerator) {
        this.userService = userService;
        this.pdFgenerator = pdFgenerator;
    }

    public ResponseEntity incomingFileHandler(MultipartFile multipartFile, String username) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        long size = multipartFile.getSize();
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);


        System.out.println("size: " + multipartFile.getSize());
        System.out.println("orgName: " + multipartFile.getOriginalFilename());
        System.out.println("fname: " + multipartFile.getName());
        System.out.println("content: " + multipartFile.getContentType());
        System.out.println("resours: " + multipartFile.getResource());
        System.out.println("mf: " + multipartFile);
        System.out.println(username);
        System.out.println("*" + username + "*");
        System.out.println();

        if (size == 0 || username.isEmpty()) {
            response.setUserNamePresent(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            User user = userService.findByUsername(username);
            // Check if there is storage space left
            response.setStorageLimitExceed(new InstpectFolder().inspectFolderSize(username, size, user.getMbOfStorage()));

//            Check if fileName exist.
            boolean fileNameExists = new InstpectFolder().inspectFileName(fileName, username);

            if (!fileNameExists && !response.isStorageLimitExceed()) {
                // TODO ADD TO identifier list
                response.setIdentifier(saveFile(fileName, multipartFile, username));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                System.out.println("File already exists!");
                response.setFileAlreadyExists(true);
                return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
            }
        }
    }

    private String saveFile(String fileName, MultipartFile multipartFile, String username) throws IOException {

        Path uploadPath = Paths.get("User-Files/" + username);

        String fileCode = RandomStringUtils.randomAlphanumeric(15);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
        return fileCode;
    }

    public ResponseEntity<?> outgoingFileHandler(String fileCode, String username) {
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


    public User makeUserPdf(String userId) throws JRException, IOException {
        Optional<User> dbUser = userService.getUserById(userId);
            User user = dbUser.get();
            return pdFgenerator.generateUserPDF(user);
    }
}