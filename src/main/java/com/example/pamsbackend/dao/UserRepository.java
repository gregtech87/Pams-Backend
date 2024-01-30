package com.example.pamsbackend.dao;

import com.example.pamsbackend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserByUsername(String username);

    Optional<Object> findByUsername(String username);

}
