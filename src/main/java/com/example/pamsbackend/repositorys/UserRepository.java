package com.example.pamsbackend.repositorys;

import com.example.pamsbackend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {


    Optional<User> findUserByEmail (String email);
    User findByEmail(String email);

    User findByUsername(String username);
    User findByConfirmationTokenToken (String token);
}
