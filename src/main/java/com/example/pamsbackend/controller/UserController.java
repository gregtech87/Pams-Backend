package com.example.pamsbackend.controller;

import com.example.pamsbackend.dao.UserRepository;
import com.example.pamsbackend.entity.User;
//import jakarta.annotation.security.RolesAllowed;
import com.example.pamsbackend.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @RolesAllowed({ "ROLE_ADMIN", "ROLE_USER" })
            @GetMapping("/username")
    public String currentUserName(Authentication authentication) {
            System.out.println(authentication.getAuthorities());
        return authentication.getAuthorities().toString();
    }
    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin!";
    }

    @RolesAllowed({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/user")
    public String user() {
        return "Hello User!";
    }

    @RolesAllowed({ "ROLE_ADMIN", "ROLE_USER" })
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
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
