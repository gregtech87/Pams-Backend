package com.example.pamsbackend.dao;

import com.example.pamsbackend.entity.PersonalFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PersonalFileService {
    List<PersonalFile> getAllFiles();
    Optional<PersonalFile> findById(String id);
    List<PersonalFile> findFilesByIds(List<String> fileIds);
    PersonalFile saveFile(PersonalFile file);
    PersonalFile editFiles(PersonalFile file);
    String deleteFiles(String id, String identifier);
    String deleteFiles2(MultipartFile multipartFile);
    String deleteFiles3(String jsonString) throws JsonProcessingException;
}
