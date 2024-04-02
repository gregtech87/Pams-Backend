/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.repositorys;


import com.example.pamsbackend.entity.SystemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemEntitiesRepository extends MongoRepository<SystemEntity, String> {
    SystemEntity findByUsername(String username);
}
