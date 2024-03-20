package com.example.pamsbackend.entity;

import com.example.pamsbackend.dao.BinaryDeserializer;
import com.example.pamsbackend.dao.BinarySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.catalina.LifecycleState;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "items")
public class Item {

    @Id
    private String id;
    @JsonSerialize(using = BinarySerializer.class)
    @JsonDeserialize(using = BinaryDeserializer.class)
    private Binary picture;
    private PictureData pictureData;
    private List<String> additionalPictureIds;
    private String owner;
    private String title;
    private String brand;
    private String model;
    private String category;
    private String dateOfPurchase;
    private float price;
    private Address placeOfPurchase;
    private boolean customLocation = false;
    private double customLat;
    private double customLong;
    private int weight;
    private String length;
    private String width;
    private String height;
    private String description;
    private String state;
    private String insurance;
    private int age;
    private String accessories;
    private ItemStatus status;



}
