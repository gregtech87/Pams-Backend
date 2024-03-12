package com.example.pamsbackend.entity;

public class FileUploadResponse {
    private String fileName;
    private String identifyer;
    private boolean fileAlreadyExists = false;
    private long size;

    public FileUploadResponse() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIdentifyer() {
        return identifyer;
    }

    public void setIdentifyer(String identifyer) {
        this.identifyer = identifyer;
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

    @Override
    public String toString() {
        return "FileUploadResponse{" +
                "fileName='" + fileName + '\'' +
                ", identifyer='" + identifyer + '\'' +
                ", fileAlreadyExists=" + fileAlreadyExists +
                ", size=" + size +
                '}';
    }
}
