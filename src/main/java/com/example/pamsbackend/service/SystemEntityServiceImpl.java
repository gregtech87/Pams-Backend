package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.SystemEntityService;
import com.example.pamsbackend.entity.SystemEntity;
import com.example.pamsbackend.repositorys.SystemEntitiesRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemEntityServiceImpl implements SystemEntityService {


    private final SystemEntitiesRepository systemEntitiesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SystemEntityServiceImpl(SystemEntitiesRepository systemEntitiesRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.systemEntitiesRepository = systemEntitiesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<SystemEntity> getAllEntities() {
        return systemEntitiesRepository.findAll();
    }

    @Override
    public Optional<SystemEntity> getEntityById(String id) {
        return systemEntitiesRepository.findById(id);
    }

    @Override
    public String createEntity(SystemEntity newEntity) {
        String encodedPassword = bCryptPasswordEncoder.encode(newEntity.getPassword());
        newEntity.setId(new ObjectId().toString());
        newEntity.setPassword(encodedPassword);
        systemEntitiesRepository.save(newEntity);
        return "Entity: " + newEntity.getUsername() + " created!";
    }

    @Override
    public void deleteEntity(String id) {

    }

    @Override
    public SystemEntity findByUsername(String username) {
        return systemEntitiesRepository.findByUsername(username);
    }
}
