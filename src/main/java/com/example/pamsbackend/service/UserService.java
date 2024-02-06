
package com.example.pamsbackend.service;

import com.example.pamsbackend.entity.Role;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.dao.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    /*Find by ObjectID
    @Autowired
    private MongoTemplate mongoTemplate;

    public YourDocument findByObjectIdFromDatabase(ObjectId objectId) {
        return mongoTemplate.findById(objectId, YourDocument.class);
    }*/

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        user.setId(new ObjectId().toString());

        /* Logik för validering username och mail */

        for (Role role : user.getRoles()) {
            role.setId(new ObjectId().toString());;
            role.setRole("ROLE_USER");
        };
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public boolean verifyUser(String username){
        boolean verified = true;
        List<User> userList = getAllUsers();
        for (User u: userList){
            if (u.getUsername().equals(username)) {
                verified = false;
                break;
            }
        }
        return verified;
    }

    public boolean verifyEmail(String email) {
        boolean verified = true;
        List<User> userList = getAllUsers();
        for (User u: userList){
            if (u.getEmail().equals(email)) {
                verified = false;
                break;
            }
        }
        return verified;
    }
}
