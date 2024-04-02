/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.dao;

import com.example.pamsbackend.entity.SystemEntity;

import java.util.List;
import java.util.Optional;

public interface SystemEntityService {

    List<SystemEntity> getAllEntities();
    Optional<SystemEntity> getEntityById(String id);
    String createEntity(SystemEntity newEntity);
    void deleteEntity(String id);
    SystemEntity findByUsername(String username);

}
