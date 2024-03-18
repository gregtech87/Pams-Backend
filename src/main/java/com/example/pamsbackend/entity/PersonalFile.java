package com.example.pamsbackend.entity;

public class PersonalFile {
    private String id;
    private String fileName;
    private String identifier;
    private String createdAt;
    private long size;

    public PersonalFile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PersonalFile{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", identifier='" + identifier + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", size=" + size +
                '}';
    }
}
