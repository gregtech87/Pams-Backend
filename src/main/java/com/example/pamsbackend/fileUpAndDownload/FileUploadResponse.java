package com.example.pamsbackend.fileUpAndDownload;

public class FileUploadResponse {
    private String fileName;
    private String identifier;
    private boolean fileAlreadyExists = false;
    private boolean userNamePresent = true;
    private boolean storageLimitExceed = false;
    private long size;

    public FileUploadResponse() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isFileAlreadyExists() {
        return fileAlreadyExists;
    }

    public void setFileAlreadyExists(boolean fileAlreadyExists) {
        this.fileAlreadyExists = fileAlreadyExists;
    }

    public boolean isUserNamePresent() {
        return userNamePresent;
    }

    public void setUserNamePresent(boolean userNamePresent) {
        this.userNamePresent = userNamePresent;
    }

    public boolean isStorageLimitExceed() {
        return storageLimitExceed;
    }

    public void setStorageLimitExceed(boolean storageLimitExceed) {
        this.storageLimitExceed = storageLimitExceed;
    }

    @Override
    public String toString() {
        return "FileUploadResponse{" +
                "fileName='" + fileName + '\'' +
                ", identifyer='" + identifier + '\'' +
                ", fileAlreadyExists=" + fileAlreadyExists +
                ", userNamePresent=" + userNamePresent +
                ", storageLimitExceed=" + storageLimitExceed +
                ", size=" + size +
                '}';
    }
}