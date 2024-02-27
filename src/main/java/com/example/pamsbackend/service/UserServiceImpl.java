
package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.EmailSender;
import com.example.pamsbackend.dao.UserService;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.repositorys.UserRepository;
import com.example.pamsbackend.entity.ConfirmationToken;
import org.apache.catalina.manager.host.HostManagerServlet;
import org.apache.catalina.startup.HostConfig;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class UserServiceImpl implements UserService {

    /*Find by ObjectID
    @Autowired
    private MongoTemplate mongoTemplate;

    public YourDocument findByObjectIdFromDatabase(ObjectId objectId) {
        return mongoTemplate.findById(objectId, YourDocument.class);
    }*/

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public String signUpUser(User newUser) {
        newUser.setId(new ObjectId().toString());
        System.out.println("****** NEW USER***: " + newUser);

        boolean verifiedUsername = true;
        boolean verifiedEmail = true;
        User userFromDbByUsername = userRepository.findByUsername(newUser.getUsername());
        User userFromDbByEmail = userRepository.findByEmail(newUser.getEmail());

        if (userFromDbByUsername != null && newUser.getUsername().equals(userFromDbByUsername.getUsername())) {
                verifiedUsername = false;
        }
        if (userFromDbByEmail != null && newUser.getEmail().equals(userFromDbByEmail.getEmail())) {
                verifiedEmail = false;
        }

        if (verifiedUsername && verifiedEmail) {
            newUser.setRole("ROLE_USER");
            String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
            newUser.setPassword(encodedPassword);
            newUser = emailService.generateToken(newUser);

            System.out.println("****** NEW USER2 ***: " + newUser);
            userRepository.save(newUser);
            return "{\"answer\":\"User registered successfully!\", \"verified\":true, " +
                    "\"verifiedUsername\":true, \"verifiedEmail\":true}";
        } else {
            return "{\"answer\":\"User not registered!\", \"verified\":false, " +
                    "\"verifiedUsername\":" + verifiedUsername + ", \"verifiedEmail\":" + verifiedEmail + "}";
        }
    }

    @Override
    public String updateUser(User editedUser) {
        System.out.println(editedUser);
        return "{\"answer\":\"User updated successfully!\"}";
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
        System.out.println("confirmationToken confirmation before: " + confirmationToken);
        if (confirmationToken.getConfirmedAt() != null) {
            return "Account already confirmed!";
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (!expiredAt.isBefore(LocalDateTime.now())) {
            user.getConfirmationToken().setConfirmedAt(LocalDateTime.now());
            user.setEnabled(true);
            System.out.println("confirmationToken confirmation after: "+user.getConfirmationToken());
            System.out.println(userRepository.save(user));
            return "Account confirmed!";
        } else {
            return "Token expired!";
        }
    }

    @Override
    public String getUserStatus(String credentials) {
        int colonIndex = credentials.indexOf(":");
        System.out.println(colonIndex);
        String username = credentials.substring(0,colonIndex);
        String password = credentials.substring(colonIndex + 1);
        System.out.println(username);
        System.out.println(password);

        User user = findByUsername(username);

        if(user != null){
            boolean verifiedPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
            return "{\"userFound\":true, \"verifiedPassword\":" + verifiedPassword + ", \"accountEnabled\":" + user.getEnabled() + ", \"accountLocked\":" + user.getLocked() + "}";
        } else {
            return "{\"userFound\":false, \"verifiedPassword\":false, \"accountEnabled\":false,\"accountLocked\":false}";
        }

    }
}
