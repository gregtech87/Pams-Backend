package com.example.pamsbackend.controller;

import com.example.pamsbackend.SystemData;
import com.example.pamsbackend.entity.Address;
import com.example.pamsbackend.entity.SystemEntity;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.service.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
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
    public void loadSystemData() throws UnknownHostException {
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();

        System.out.println(System.getProperty("spring.data.mongodb.host"));
        systemData.load();
        User u = userServiceimpl.findByUsername("testGuy");
        if(u == null){
            User user = new User("t@g.com","testGuy", "testGuy", "testGuy", "ROLE_USER");
            user.setEnabled(true);
            user.setUsername("testGuy");
            byte[] decodedBytes = Base64.getDecoder().decode("noImages");
            user.setProfilePic(new Binary(decodedBytes));
            user.setAddress(new Address("street", 4654, "city"));
            user.setPhone("646843135");
            user.setDateOfBirth("2154-12-05");
            userServiceimpl.signUpUser(user);
            System.out.println(user);
            System.out.println("testGuy created !");
        } else {
            System.out.println("testGuy present!");
        }
    }

    @GetMapping("/hello")
    public String sadasd(){
        return "HEEEEEEEEEOOOOLLLLLLOOOOOOO";
    }
    @GetMapping("/login")
    public User currentUserName(Authentication authentication) {
        User user = userServiceimpl.findByUsername(authentication.getName());
        System.out.println("que: "+user);
        System.out.println(authentication.toString());
        System.out.println(authentication.getAuthorities());
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userServiceimpl.getAllUsers();
    }
//
//    @GetMapping("/token/{tokenString}")
//    public User getTokenUser(@PathVariable String tokenString) {
//        return userServiceimpl.getToken(tokenString);
//    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable String id) {
        System.out.println(id);
        System.out.println(userServiceimpl.getUserById(id));
        return userServiceimpl.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
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
        System.out.println("***************** CONFIRMING *****************");
        return userServiceimpl.confirmToken(token);
    }

    @GetMapping("/userstatus")
    public String getStatus(@RequestParam("credentials") String credentials){
        return userServiceimpl.getUserStatus(credentials);
    }

    @PostMapping("/userPassword")
    public Object userPassword(@RequestBody String password) {
        return userServiceimpl.updateUserPassword(password);
    }

}
