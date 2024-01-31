package com.example.pamsbackend.dao;


import com.example.pamsbackend.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author didin
 */
public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);

}
