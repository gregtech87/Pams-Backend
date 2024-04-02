/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.fileUpAndDownload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InspectFolder {
    private boolean fileNameExists = false;
    private boolean StorageLimitExceed = false;

    public InspectFolder() {
    }

    /*
     * Checks if folder contain file with same name.
     * If error finding folder, create it.
     * */
    public boolean inspectFileName(String fileName, String username, Path uploadPath) throws IOException {

        try {
            Files.list(uploadPath).forEach(file -> {
                if (file.getFileName().toString().endsWith(fileName)) {
                    fileNameExists = true;
                }
            });
        } catch (IOException e) {
            if (!Files.exists(uploadPath)) {
                System.out.println("******************* NEW DIRECTORY CREATED: " + uploadPath + " ******************");
                Files.createDirectories(uploadPath);
            }
            System.out.println("First time user: " + username + " Uploads a file!");
        }
        return fileNameExists;
    }

    public boolean inspectFolderSize(String username, long fileSize, long mbOfStorage, Path uploadPath) {
        long maxSizePerUser = 1024*1024*mbOfStorage;
        long size = 0;
        try {
            size = Files.list(uploadPath).mapToLong(file -> {
                        try {
                            return Files.size(file);
                        } catch (IOException e) {
                            System.err.println("Error getting size of file: " + file);
                            return 0;
                        }
                    })
                    .sum();
        } catch (IOException e) {
            System.err.println("Problem finding folder!");
        }
        size = size + fileSize;

        if (maxSizePerUser < size){
            StorageLimitExceed = true;
            System.err.println("Max storage exceeded for user: " + username);
            System.err.println("File not saved!");
        }
        return StorageLimitExceed;
    }
}
