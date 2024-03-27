package com.example.pamsbackend.fileUpAndDownload;

import com.example.pamsbackend.PdfUserInfo.PDFgenerator;
import com.example.pamsbackend.dao.ItemService;
import com.example.pamsbackend.dao.PersonalFileService;
import com.example.pamsbackend.dao.UserService;
import com.example.pamsbackend.entity.Item;
import com.example.pamsbackend.entity.PersonalFile;
import com.example.pamsbackend.entity.User;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@Service
public class FileUploadUtil {

    private final UserService userService;
    private final PersonalFileService personalFileService;
    private final ItemService itemService;
    private final PDFgenerator pdFgenerator;

    @Autowired
    public FileUploadUtil(UserService userService, PersonalFileService personalFileService, ItemService itemService, PDFgenerator pdFgenerator) {
        this.userService = userService;
        this.personalFileService = personalFileService;
        this.itemService = itemService;
        this.pdFgenerator = pdFgenerator;
    }

    public ResponseEntity incomingFileHandler(MultipartFile multipartFile, String username, String itemId) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        long size = multipartFile.getSize();
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        long maxFilesize = response.getMaxFileSize() * 1024 * 1024;


        System.out.println("FileUploadUtil.incomingFileHandler");
        System.out.println("size: " + multipartFile.getSize());
        System.out.println("orgName: " + multipartFile.getOriginalFilename());
        System.out.println("fname: " + multipartFile.getName());
        System.out.println("content: " + multipartFile.getContentType());
        System.out.println("resours: " + multipartFile.getResource());
        System.out.println("mf: " + multipartFile);
        System.out.println(username);
        System.out.println("*" + username + "*");
        System.out.println("itemId = " + itemId);
        System.out.println();


        if (size > maxFilesize) {
            response.setFileSizeExceed(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (size == 0 || username.isEmpty()) {
            response.setUserNamePresent(false);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            User user = userService.findByUsername(username);

            Path uploadPath = null;
            Item item = null;
            if (itemId == null) {
                uploadPath = Paths.get("User-Files/" + user.getUsername());
            } else {
                Optional<Item> dbItem = itemService.findById(itemId);
                if (dbItem.isPresent()) {
                    item = dbItem.get();
                    uploadPath = Paths.get("User-Files/" + user.getUsername() + "/" + item.getTitle());
                }
            }

            // Check if there is storage space left
            response.setStorageLimitExceed(new InstpectFolder().inspectFolderSize(username, size, user.getMbOfStorage(), uploadPath));
            System.out.println("response = " + response);
//            Check if fileName exist.
            boolean fileNameExists = new InstpectFolder().inspectFileName(fileName, username, uploadPath);
            System.out.println("***************************************************************");
            System.out.println("FileUploadUtil.incomingFileHandler");

            if (!fileNameExists && !response.isStorageLimitExceed() && itemId == null) {
                System.out.println("user = " + user);
                response.setIdentifier(saveFile(fileName, multipartFile, user, uploadPath, null));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (!fileNameExists && !response.isStorageLimitExceed() && itemId != null) {
                assert uploadPath != null;
                System.out.println("user = " + user);
                response.setIdentifier(saveFile(fileName, multipartFile, user, uploadPath, item));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                System.out.println("File already exists!");
                response.setFileAlreadyExists(true);
                return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
            }
        }
    }

    private String saveFile(String fileName, MultipartFile multipartFile, User user, Path uploadPath, Item item) throws IOException {

//        Path uploadPath = null;
//        Item item = null;
//        if (itemId == null){
//            uploadPath = Paths.get("User-Files/" + user.getUsername());
//        } else {
//            Optional<Item> dbItem = itemService.findById(itemId);
//            if (dbItem.isPresent()) {
//                item = dbItem.get();
//                uploadPath = Paths.get("User-Files/" + user.getUsername() + "/" +item.getTitle());
//            }
//
//        }

        String fileCode = RandomStringUtils.randomAlphanumeric(15);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            assert uploadPath != null;
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
        if (item == null) {
            addToPersonalFilesList(multipartFile, fileCode, user);
        } else {
            addToItemsImageList(multipartFile, fileCode, user, item);
        }
        return fileCode;
    }

    public User makeUserPdf(String userId) throws JRException, IOException {
        Optional<User> dbUser = userService.findUserById(userId);
        if (dbUser.isPresent()) {
            User user = dbUser.get();
            return pdFgenerator.generateUserPDF(user);
        }
        return null;
    }

    private void addToPersonalFilesList(MultipartFile multipartFile, String fileCode, User user) {
        PersonalFile personalFile = setFileAttribute(multipartFile, fileCode);
        user.getPersonalFiles().add(personalFile.getId());
        user.setUsedStorage(user.getUsedStorage() + multipartFile.getSize());
        userService.save(user);
    }

    private void addToItemsImageList(MultipartFile multipartFile, String fileCode, User user, Item item) {
        PersonalFile personalFile = setFileAttribute(multipartFile, fileCode);
        item.getAdditionalPictureIds().add(personalFile.getId());
        itemService.saveItem(item);
        user.setUsedStorage(user.getUsedStorage() + multipartFile.getSize());
        userService.save(user);

    }

    private PersonalFile setFileAttribute(MultipartFile multipartFile, String fileCode) {
        PersonalFile personalFile = new PersonalFile();
        personalFile.setId(new ObjectId().toString());
        personalFile.setFileName(multipartFile.getOriginalFilename());
        personalFile.setType(multipartFile.getContentType());
        personalFile.setSize(multipartFile.getSize());
        personalFile.setIdentifier(fileCode);
        personalFile.setCreatedAt(LocalDateTime.now().format(ISO_DATE_TIME));
        personalFileService.saveFile(personalFile);
        return personalFile;
    }
}