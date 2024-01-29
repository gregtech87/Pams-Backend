package com.example.pamsbackend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "addresses")
public class Address {

//    @Id
//    private String addressId; // MongoDB uses a String for the ID by default


    private String street;

    private int postalCode;

    private String city;

    public Address() {
    }

//    public String getAddressId() {
//        return addressId;
//    }

//    public void setAddressId(String addressId) {
//        this.addressId = addressId;
//    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
//                "addressId='" + addressId + '\'' +
                ", street='" + street + '\'' +
                ", postalCode=" + postalCode +
                ", city='" + city + '\'' +
                '}';
    }
}
