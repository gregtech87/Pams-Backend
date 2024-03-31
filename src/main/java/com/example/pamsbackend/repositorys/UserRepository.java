package com.example.pamsbackend.repositorys;

import com.example.pamsbackend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findByConfirmationTokenToken (String token);
}
