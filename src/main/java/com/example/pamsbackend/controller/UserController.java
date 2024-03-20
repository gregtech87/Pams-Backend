package com.example.pamsbackend.controller;

import com.example.pamsbackend.PdfUserInfo.PDFgenerator;
import com.example.pamsbackend.PdfUserInfo.PdfUser;
import com.example.pamsbackend.SystemData;
import com.example.pamsbackend.dao.NoteService;
import com.example.pamsbackend.entity.Address;
import com.example.pamsbackend.entity.PictureData;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.service.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import net.sf.jasperreports.engine.*;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.Inet4Address;
import java.util.*;

@RestController
@CrossOrigin(origins = "https://pam-gui.gregtech.duckdns.org")
@RequestMapping("/api/v1")
public class UserController {

    private final UserServiceImpl userServiceimpl;
    private final SystemData systemData;
    private final NoteService noteService;
    private final PDFgenerator pdFgenerator;


    @Autowired
    public UserController(UserServiceImpl userServiceimpl, SystemData systemData, NoteService noteService, PDFgenerator pdFgenerator) {
        this.userServiceimpl = userServiceimpl;
        this.systemData = systemData;
        this.noteService = noteService;
        this.pdFgenerator = pdFgenerator;
    }

    @PostConstruct
    public void loadSystemData() throws IOException, JRException {
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        systemData.load();

        User u = userServiceimpl.findByUsername("testGuy");
        if (u == null) {
            User user = new User("t@g.com", "testGuy", "testGuy", "testGuy", "ROLE_USER");
            user.setEnabled(true);
            user.setUsername("testGuy");
            byte[] decodedBytes = Base64.getDecoder().decode("noImages");
            user.setProfilePic(new Binary(decodedBytes));
            user.setProfilePictureData(new PictureData("name", "type", 0, 0, "LMD"));
            user.setAddress(new Address("bjärme", 150, 85592, "kovland"));
            user.setPhone("646843135");
            user.setDateOfBirth("2154-12-05");
            user.setPdfUser(new PdfUser());
            userServiceimpl.signUpUser(user);
            System.out.println(user);
            System.out.println("testGuy created !");
//            pdFgenerator.generateUserPDF(user);
        } else {
            System.out.println("testGuy present!");
//            pdFgenerator.generateUserPDF(u);
        }



    }

    @GetMapping("/hello")
    public String sadasd() {
        return "HEEEEEEEEEOOOOLLLLLLOOOOOOO";
    }

    @GetMapping("/login")
    public User currentUserName(Authentication authentication) {
        User user = userServiceimpl.findByUsername(authentication.getName());
        System.out.println("que: " + user);
        System.out.println(authentication.toString());
        System.out.println(authentication.getAuthorities());
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userServiceimpl.findAllUsers();
    }
//
//    @GetMapping("/token/{tokenString}")
//    public User getTokenUser(@PathVariable String tokenString) {
//        return userServiceimpl.getToken(tokenString);
//    }

    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        System.out.println(id);
        System.out.println(userServiceimpl.findUserById(id));
        return userServiceimpl.findUserById(id);
    }

    @PostMapping("/user")
    public Object NewUser(@RequestBody User user) {
        return userServiceimpl.signUpUser(user);
    }

    @PutMapping("/user")
    public Object UpdateUser(@RequestBody User user) {
        System.out.println("********** USER ****controller***********" + user);
        return userServiceimpl.updateUser(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable String id) {
        userServiceimpl.deleteUser(id);
    }

    @GetMapping("/registration/confirm")
    public String confirmUser(@RequestParam("token") String token) {
        System.out.println("***************** CONFIRMING *****************");
        return userServiceimpl.confirmToken(token);
    }

    @GetMapping("/userstatus")
    public String getStatus(@RequestParam("credentials") String credentials) {
        return userServiceimpl.getUserStatus(credentials);
    }

    @PostMapping("/userPassword")
    public Object userPassword(@RequestBody String password) {
        return userServiceimpl.updateUserPassword(password);
    }

}
