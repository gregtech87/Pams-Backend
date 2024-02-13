package com.example.pamsbackend.dao;

public interface EmailSender {
    void send(String to, String email);
}
