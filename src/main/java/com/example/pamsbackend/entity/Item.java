package com.example.pamsbackend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "items")
public class Item {

    @Id
    private String id;
    private String title;
    private String brand;
    private String model;
    private String category;
    private String dateOfPurchase;
    private int price;
    private String placeOfPurchase;
    private int weight;
    private String size;
    private String state;
    private String insurance;
    private int age;
    private String accessories;
    private String status;



}
