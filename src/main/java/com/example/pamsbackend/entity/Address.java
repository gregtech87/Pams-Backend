package com.example.pamsbackend.entity;

public class Address {

    private String street;

    private int postalCode;

    private String city;

    public Address() {
    }

    public Address(String street, int postalCode, String city) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

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
                ", street='" + street + '\'' +
                ", postalCode=" + postalCode +
                ", city='" + city + '\'' +
                '}';
    }
}
