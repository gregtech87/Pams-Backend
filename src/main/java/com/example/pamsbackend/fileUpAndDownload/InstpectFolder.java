package com.example.pamsbackend.fileUpAndDownload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InstpectFolder {
    private boolean fileNameExists = false;
    private boolean StorageLimitExceed = false;

    // Set max storage per user in MB with last number.


    public InstpectFolder() {
    }

    /*
     * Checks if folder contain file with same name.
     * If error finding folder, create it.
     * */
    public boolean inspectFileName(String fileName, String username) throws IOException {
        System.out.println("InstpectFolder.inspectFileName");
        System.out.println("fileName = " + fileName);

        Path userPath = Paths.get("User-Files/" + username);
        try {
            Files.list(userPath).forEach(file -> {
                if (file.getFileName().toString().endsWith(fileName)) {
                    fileNameExists = true;
                }
            });
        } catch (IOException e) {
            if (!Files.exists(userPath)) {
                System.out.println("******************* NEW DIRECTORY CREATED: " + userPath + " ******************");
                Files.createDirectories(userPath);
            }
            System.out.println("First time user: " + username + " Uploads a file!");
        }
        return fileNameExists;
    }

    public boolean inspectFolderSize(String username, long fileSize, long mbOfStorage) {
        long maxSizePerUser = 1024*1024*mbOfStorage;
        Path userPath = Paths.get("User-Files/" + username);
        long size = 0;
        try {
            size = Files.list(userPath).mapToLong(file -> {
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
        System.out.println("maxSizePerUser = " + maxSizePerUser);
        System.out.println("username = " + username);
        System.out.println("fileSize = " + fileSize);
        System.out.println("mbOfStorage = " + mbOfStorage);
        System.out.println("size = " + size);

        if (maxSizePerUser < size){
            StorageLimitExceed = true;
            System.err.println("Max storage exceeded for user: "+username);
            System.err.println("File not saved!");
        }
        return StorageLimitExceed;
    }
}
