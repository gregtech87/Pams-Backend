package com.example.pamsbackend.fileUpAndDownload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InstpectFolder {
    private boolean fileNameExists = false;

    public InstpectFolder() {
    }

    /*
    * Checks if folder contain file with same name.
    * If error finding folder, create it.
    * */
    public boolean inspect(String fileName, String username) throws IOException {

        Path userPath = Paths.get("User-Files/" + username);
        try{
            Files.list(userPath).forEach(file -> {
                if (file.getFileName().toString().endsWith(fileName)) {
                    fileNameExists = true;
                }
            });
        } catch (IOException e) {
            if (!Files.exists(userPath)) {
                Files.createDirectories(userPath);
            }
            System.out.println("First time user: " + username + " Uploads a file!");
        }
        return fileNameExists;
    }
}
