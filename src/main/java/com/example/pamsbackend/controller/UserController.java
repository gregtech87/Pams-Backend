package com.example.pamsbackend.controller;

import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public User currentUserName(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        System.out.println(user);
        System.out.println(authentication.toString());
        System.out.println(authentication.getAuthorities());
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        System.out.println(id);
        System.out.println(userService.getUserById(id));
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @PostMapping("/users")
    public Object saveUser(@RequestBody User user) {
//        /* flytta skiten, lägg till find user by username() */
//        System.out.println("****** NEW USER***: "+user);
//        boolean verifiedUsername = userService.verifyUser(user.getUsername());
//        boolean verifiedEmail = userService.verifyEmail(user.getEmail());
//
//        if(verifiedUsername && verifiedEmail){
//            userService.saveUser(user);
//            return "{\"answer\":\"User registered successfully\", \"verified\":true, " +
//                    "\"verifiedUsername\":true, \"verifiedEmail\":true}";
//        }else {
//            return "{\"answer\":\"User not registered\", \"verified\":false, " +
//                    "\"verifiedUsername\":"+verifiedUsername+", \"verifiedEmail\":" + verifiedEmail + "}";
//        }
        return userService.saveUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
