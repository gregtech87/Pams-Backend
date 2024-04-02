/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.dao;

import com.example.pamsbackend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers();
    Optional<User> findUserById(String id);
    String signUpUser(User newUser) ;
    String updateUser(User editedUser) ;
    void deleteUser(String id);
    User findByUsername(String username);
    String confirmToken(String token);
    String getUserStatus(String username);
    boolean updateUserPassword(String idOldNewPassword);
    void save(User user);
}
