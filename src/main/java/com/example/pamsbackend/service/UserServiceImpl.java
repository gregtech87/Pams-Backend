
package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.EmailSender;
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

//        boolean verifiedUsername = true;
//        boolean verifiedEmail = true;
//        User userFromDbByUsername = userRepository.findByUsername(newUser.getUsername());
//        User userFromDbByEmail = userRepository.findByEmail(newUser.getEmail());
//
//        if (userFromDbByUsername != null && newUser.getUsername().equals(userFromDbByUsername.getUsername())) {
//                verifiedUsername = false;
//        }
//        if (userFromDbByEmail != null && newUser.getEmail().equals(userFromDbByEmail.getEmail())) {
//                verifiedEmail = false;
//        }
//
//        if (verifiedUsername && verifiedEmail) {
//            newUser.setRole("ROLE_USER");
//            String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
//            newUser.setPassword(encodedPassword);
//            newUser = emailService.generateToken(newUser);
//
//            System.out.println("****** NEW USER2 ***: " + newUser);
//            userRepository.save(newUser);
//            return "{\"answer\":\"User registered successfully!\", \"verified\":true, " +
//                    "\"verifiedUsername\":true, \"verifiedEmail\":true}";
//        } else {
//            return "{\"answer\":\"User not registered!\", \"verified\":false, " +
//                    "\"verifiedUsername\":" + verifiedUsername + ", \"verifiedEmail\":" + verifiedEmail + "}";
//        }
        return checkUsernameAndEmailInDatabase(newUser, false);
    }

    private String checkUsernameAndEmailInDatabase(User user, boolean editedUser) {
        boolean verifiedUsername = true;
        boolean verifiedEmail = true;
//        User userFromDbByUsername = userRepository.findByUsername(user.getUsername());
//        User userFromDbByEmail = userRepository.findByEmail(user.getEmail());
//
//        if (userFromDbByUsername != null && !editedUser && user.getUsername().equals(userFromDbByUsername.getUsername())) {
//            verifiedUsername = false;
//        }
//        if (userFromDbByEmail != null && !editedUser && user.getEmail().equals(userFromDbByEmail.getEmail())) {
//            verifiedEmail = false;
//        }

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
        } catch (NullPointerException n){
            System.out.println("No users registered");
        }

        if (verifiedUsername && verifiedEmail) {
            if (!editedUser) {
                user.setRole("ROLE_USER");
                String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                user = emailService.generateToken(user);
            }

            System.out.println("****** NEW USER2 ***: " + user);
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
            System.out.println("DB User: " + user);
            editedUser.setPassword(user.getPassword());
            user = editedUser;
            System.out.println("DB User: " + user);
//            user.setFirstName(editedUser.getFirstName());
//            user.setLastName(editedUser.getLastName());
//            user.setUsername(editedUser.getUsername());
//            user.setEmail(editedUser.getEmail());
//            user.setPhone(editedUser.getPhone());
//            user.setDateOfBirth(editedUser.getDateOfBirth());
//            user.setAddress(new Address(
//                    editedUser.getAddress().getStreet(),
//                    editedUser.getAddress().getPostalCode(),
//                    editedUser.getAddress().getCity()
//                    ));
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
        System.out.println("confirmationToken confirmation before: " + confirmationToken);
        if (confirmationToken.getConfirmedAt() != null) {
            return "Account already confirmed!";
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (!expiredAt.isBefore(LocalDateTime.now())) {
            user.getConfirmationToken().setConfirmedAt(LocalDateTime.now());
            user.setEnabled(true);
            System.out.println("confirmationToken confirmation after: " + user.getConfirmationToken());
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
        String username = credentials.substring(0, colonIndex);
        String password = credentials.substring(colonIndex + 1);
        System.out.println(username);
        System.out.println(password);

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
        int colonIndex1 = idOldNewPassword.indexOf(":");
        String userId = idOldNewPassword.substring(1, colonIndex1);
        String onlyPasswords = idOldNewPassword.substring(colonIndex1 + 1, idOldNewPassword.length() - 1);

        int colonIndex2 = onlyPasswords.indexOf(":");
        String oldPassword = onlyPasswords.substring(0, colonIndex2);
        String newpassword = onlyPasswords.substring(colonIndex2 + 1);

        System.out.println(idOldNewPassword);
        System.out.println(userId);
        System.out.println(oldPassword);
        System.out.println(newpassword);

        boolean success = false;

        Optional<User> dbUser = userRepository.findById(userId);
        if (dbUser.isPresent()) {
            User user = dbUser.get();
            success = bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
            if (success){
                user.setPassword(bCryptPasswordEncoder.encode(newpassword));
                userRepository.save(user);
            }
        }
        return success;
    }

    public void save(User user){
        userRepository.save(user);
    }
}
