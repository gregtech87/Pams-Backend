package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.ItemServiceToPersonalFilesServiceConnectorService;
import com.example.pamsbackend.dao.PersonalFileService;
import com.example.pamsbackend.entity.PersonalFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceToPersonalFilesServiceConnectorImpl implements ItemServiceToPersonalFilesServiceConnectorService {

    private final PersonalFileService personalFileService;

    @Autowired
    public ItemServiceToPersonalFilesServiceConnectorImpl(PersonalFileService personalFileService) {
        this.personalFileService = personalFileService;
    }

    @Override
    public boolean removeAdditionalPictures(List<String> idList) {
        boolean success = false;
        if (idList.isEmpty()) {
            success = true;
        } else {
            List<PersonalFile> fileList = personalFileService.findFilesByIds(idList);
            System.out.println("fileList = " + fileList);
        }
        return success;
    }
}
