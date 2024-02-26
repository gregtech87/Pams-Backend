package com.example.pamsbackend;

import com.example.pamsbackend.entity.SystemEntity;
import com.example.pamsbackend.service.SystemEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemData {

    public static List<String> entityList = new ArrayList<>();
    private final SystemEntityServiceImpl systemEntityService;

    @Autowired
    public SystemData(SystemEntityServiceImpl systemEntityService) {
        this.systemEntityService = systemEntityService;
    }


    public void load(){
        System.out.println("Loading System Data:");
        if(systemEntityService.findByUsername("newUser") == null){
            SystemEntity systemEntity = new SystemEntity("newUser", "newUser", "newUser", "ROLE_NEWUSER");
            System.out.println(systemEntityService.createEntity(systemEntity));
        } else {
            System.out.println("newUser present!");
        }

        if(systemEntityService.findByUsername("statusCheck") == null){
            SystemEntity systemEntity = new SystemEntity("statusCheck", "statusCheck", "statusCheck", "ROLE_STATUSCHECK");
            System.out.println(systemEntityService.createEntity(systemEntity));
        } else {
            System.out.println("statusCheck present!");
        }

        if(systemEntityService.findByUsername("editUser") == null){
            SystemEntity systemEntity = new SystemEntity("editUser", "editUser", "editUser", "ROLE_EDITUSER");
            System.out.println(systemEntityService.createEntity(systemEntity));
        } else {
            System.out.println("editUser present!");
        }

        setEntityList();

    }

    private void setEntityList() {
        List<SystemEntity> entities = systemEntityService.getAllEntities();
        for(SystemEntity se: entities){
            entityList.add(se.getUsername());
        }
        System.out.println("All Entities: " + entityList);
    }

    public static List<String> getEntityList() {
        return entityList;
    }
}
