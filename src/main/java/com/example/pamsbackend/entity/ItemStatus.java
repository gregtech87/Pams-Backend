package com.example.pamsbackend.entity;

public class ItemStatus {
    private String state;
    private Address currentLocation;
    private String nameOfHolder;
    private String purpose;

    public ItemStatus() {
    }

    public ItemStatus(String state, String nameOfHolder, String purpose) {
        this.state = state;
        this.nameOfHolder = nameOfHolder;
        this.purpose = purpose;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Address getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Address currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getNameOfHolder() {
        return nameOfHolder;
    }

    public void setNameOfHolder(String nameOfHolder) {
        this.nameOfHolder = nameOfHolder;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "ItemStatus{" +
                "state='" + state + '\'' +
                ", currentLocation=" + currentLocation +
                ", nameOfHolder='" + nameOfHolder + '\'' +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
