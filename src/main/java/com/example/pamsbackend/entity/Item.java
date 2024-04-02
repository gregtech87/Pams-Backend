/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.entity;

import com.example.pamsbackend.dao.BinaryDeserializer;
import com.example.pamsbackend.dao.BinarySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "items")
public class Item {

    @Id
    private String id;
    @JsonSerialize(using = BinarySerializer.class)
    @JsonDeserialize(using = BinaryDeserializer.class)
    private Binary profilePic;
    private PictureData profilePictureData;
    private String owner;
    private String title;
    private String brand;
    private String model;
    private String category;
    private String dateOfPurchase;
    private float price;
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
    private String age;
    private String accessories;
    private String createdAt;
    private List<String> additionalPictureIds = new ArrayList<>();
    private Address placeOfPurchase;
    private ItemStatus status;

    public Item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Binary getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Binary profilePic) {
        this.profilePic = profilePic;
    }

    public PictureData getProfilePictureData() {
        return profilePictureData;
    }

    public void setProfilePictureData(PictureData profilePictureData) {
        this.profilePictureData = profilePictureData;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isCustomLocation() {
        return customLocation;
    }

    public void setCustomLocation(boolean customLocation) {
        this.customLocation = customLocation;
    }

    public double getCustomLat() {
        return customLat;
    }

    public void setCustomLat(double customLat) {
        this.customLat = customLat;
    }

    public double getCustomLong() {
        return customLong;
    }

    public void setCustomLong(double customLong) {
        this.customLong = customLong;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAccessories() {
        return accessories;
    }

    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getAdditionalPictureIds() {
        return additionalPictureIds;
    }

    public void setAdditionalPictureIds(List<String> additionalPictureIds) {
        this.additionalPictureIds = additionalPictureIds;
    }

    public Address getPlaceOfPurchase() {
        return placeOfPurchase;
    }

    public void setPlaceOfPurchase(Address placeOfPurchase) {
        this.placeOfPurchase = placeOfPurchase;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", picture=" + profilePic +
                ", pictureData=" + profilePictureData +
                ", owner='" + owner + '\'' +
                ", title='" + title + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", category='" + category + '\'' +
                ", dateOfPurchase='" + dateOfPurchase + '\'' +
                ", price=" + price +
                ", customLocation=" + customLocation +
                ", customLat=" + customLat +
                ", customLong=" + customLong +
                ", weight=" + weight +
                ", length='" + length + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", insurance='" + insurance + '\'' +
                ", age=" + age +
                ", accessories='" + accessories + '\'' +
                ", createdAt=" + createdAt +
                ", additionalPictureIds=" + additionalPictureIds +
                ", placeOfPurchase=" + placeOfPurchase +
                ", status=" + status +
                '}';
    }
}
