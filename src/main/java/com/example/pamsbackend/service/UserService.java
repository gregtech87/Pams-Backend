
package com.example.pamsbackend.service;

import com.example.pamsbackend.entity.Role;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.dao.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        for (Role role : user.getRoles()) {
            role.setId(new ObjectId().toString());;
        };
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
