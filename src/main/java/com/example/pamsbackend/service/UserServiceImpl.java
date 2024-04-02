
/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.service;

import com.example.pamsbackend.PdfUserInfo.PdfUser;
import com.example.pamsbackend.dao.UserService;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.repositorys.UserRepository;
import com.example.pamsbackend.entity.ConfirmationToken;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public String signUpUser(User newUser) {
        newUser.setId(new ObjectId().toString());
        return checkUsernameAndEmailInDatabase(newUser, false);
    }

    private String checkUsernameAndEmailInDatabase(User user, boolean editedUser) {
        boolean verifiedUsername = true;
        boolean verifiedEmail = true;

        List<User> userList = userRepository.findAll();
        try {
            for (User u : userList) {
                if (!u.getId().equals(user.getId()) && u.getUsername().equals(user.getUsername())) {
                    verifiedUsername = false;
                }
                if (!u.getId().equals(user.getId()) && u.getEmail().equals(user.getEmail())) {
                    verifiedEmail = false;
                }
            }
        } catch (NullPointerException n) {
            System.out.println("No users registered");
        }

        if (verifiedUsername && verifiedEmail) {

            if (!editedUser) {
                user.setRole("ROLE_USER");
                String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                user.setPdfUser(new PdfUser());
                user = emailService.generateToken(user);
            }

            userRepository.save(user);
            return "{\"answer\":\"User registered successfully!\", \"verified\":true, " +
                    "\"verifiedUsername\":true, \"verifiedEmail\":true, \"found\":true}";
        } else {
            return "{\"answer\":\"User not registered!\", \"verified\":false, \"found\":true," +
                    "\"verifiedUsername\":" + verifiedUsername + ", \"verifiedEmail\":" + verifiedEmail + "}";
        }
    }

    @Override
    public String updateUser(User editedUser) {

        Optional<User> dbUser = userRepository.findById(editedUser.getId());
        if (dbUser.isPresent()) {
            User user = dbUser.get();
            editedUser.setPassword(user.getPassword());
            user = editedUser;
            return checkUsernameAndEmailInDatabase(user, true);
        } else {
            return "{\"answer\":\"User does not exist in database!\", \"verified\":false, " +
                    "\"verifiedUsername\": false, \"verifiedEmail\": false, \"found\":false}";
        }
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public String confirmToken(String token) {
        User user = userRepository.findByConfirmationTokenToken(token);
        ConfirmationToken confirmationToken = user.getConfirmationToken();
        if (confirmationToken.getConfirmedAt() != null) {
            return "Account already confirmed!";
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (!expiredAt.isBefore(LocalDateTime.now())) {
            user.getConfirmationToken().setConfirmedAt(LocalDateTime.now());
            user.setEnabled(true);
            userRepository.save(user);
            return "Account confirmed!";
        } else {
            return "Token expired!";
        }
    }

    @Override
    public String getUserStatus(String credentials) {
        int colonIndex = credentials.indexOf(":");
        String username = credentials.substring(0, colonIndex);
        String password = credentials.substring(colonIndex + 1);
        User user = findByUsername(username);

        if (user != null) {
            boolean verifiedPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
            return "{\"userFound\":true, \"verifiedPassword\":" + verifiedPassword + ", \"accountEnabled\":" + user.getEnabled() + ", \"accountLocked\":" + user.getLocked() + "}";
        } else {
            return "{\"userFound\":false, \"verifiedPassword\":false, \"accountEnabled\":false,\"accountLocked\":false}";
        }
    }

    @Override
    public boolean updateUserPassword(String idOldNewPassword) {
        // idOldNewPassword looks like this: "id:oldPassword:newPassword"
        int colonIndex1 = idOldNewPassword.indexOf(":");
        String userId = idOldNewPassword.substring(1, colonIndex1);
        String onlyPasswords = idOldNewPassword.substring(colonIndex1 + 1, idOldNewPassword.length() - 1);

        int colonIndex2 = onlyPasswords.indexOf(":");
        String oldPassword = onlyPasswords.substring(0, colonIndex2);
        String newPassword = onlyPasswords.substring(colonIndex2 + 1);

        boolean success = false;

        Optional<User> dbUser = userRepository.findById(userId);
        if (dbUser.isPresent()) {
            User user = dbUser.get();
            success = bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
            if (success) {
                user.setPassword(bCryptPasswordEncoder.encode(newPassword));
                userRepository.save(user);
            }
        }
        return success;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
