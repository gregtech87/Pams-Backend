package com.example.pamsbackend.dao;

import com.example.pamsbackend.entity.PersonalFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PersonalFileService {
    List<PersonalFile> getAllFiles();
    Optional<PersonalFile> findById(String id);
    List<PersonalFile> findFilesByIds(List<String> fileIds);
    PersonalFile saveFile(PersonalFile file);
    PersonalFile editFiles(PersonalFile file);
    String deleteFile(String jsonString) throws IOException;
    void deleteFileEntryOnly(String id);
}
