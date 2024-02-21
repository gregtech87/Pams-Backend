package com.example.pamsbackend.dao;

import com.example.pamsbackend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();
    Optional<User> getUserById(String id);
    String signUpUser(User newUser) ;
    String updateUser(User editedUser) ;
    void deleteUser(String id);
    User findByUsername(String username);
    String confirmToken(String token);
    String getUserStatus(String username);

}
