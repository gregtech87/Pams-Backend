/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.controller;

import com.example.pamsbackend.PdfUserInfo.PdfUser;
import com.example.pamsbackend.SystemData;
import com.example.pamsbackend.entity.Address;
import com.example.pamsbackend.entity.PictureData;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.service.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import net.sf.jasperreports.engine.JRException;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://pam-gui.gregtech.duckdns.org")
@RequestMapping("/api/v1")
public class UserController {

    private final UserServiceImpl userServiceimpl;
    private final SystemData systemData;

    @Autowired
    public UserController(UserServiceImpl userServiceimpl, SystemData systemData) {
        this.userServiceimpl = userServiceimpl;
        this.systemData = systemData;
    }

    @PostConstruct
    public void loadSystemData() throws IOException {
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        // Makes sure system users exist.
        systemData.load();
        // Dummy devops guy
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
        } else {
            System.out.println("testGuy present!");
        }
    }

    @GetMapping("/login")
    public User currentUserName(Authentication authentication) {
        return userServiceimpl.findByUsername(authentication.getName());
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userServiceimpl.findAllUsers();
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userServiceimpl.findUserById(id);
    }

    @PostMapping("/user")
    public Object NewUser(@RequestBody User user) {
        return userServiceimpl.signUpUser(user);
    }

    @PutMapping("/user")
    public Object UpdateUser(@RequestBody User user) {
        return userServiceimpl.updateUser(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable String id) {
        userServiceimpl.deleteUser(id);
    }

    @GetMapping("/registration/confirm")
    public String confirmUser(@RequestParam("token") String token) {
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
