package com.example.pamsbackend.PdfUserInfo;

import java.time.LocalDateTime;

public class PdfUser {

    private String name = "UserReport.pdf";
    private String userInfoPdfIdentifier;
    private String createdAt;

    public PdfUser() {
    }

    public PdfUser(String userInfoPdfIdentifier, String createdAt) {
        this.userInfoPdfIdentifier = userInfoPdfIdentifier;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserInfoPdfIdentifier() {
        return userInfoPdfIdentifier;
    }

    public void setUserInfoPdfIdentifier(String userInfoPdfIdentifier) {
        this.userInfoPdfIdentifier = userInfoPdfIdentifier;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PdfUser{" +
                "name='" + name + '\'' +
                ", userInfoPdfIdentifier='" + userInfoPdfIdentifier + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}