/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.entity;

public class ItemStatus {

    private Address currentLocation;
    private String nameOfHolder;
    private String purpose;

    public ItemStatus() {
    }

    public ItemStatus(String nameOfHolder, String purpose) {
        this.nameOfHolder = nameOfHolder;
        this.purpose = purpose;
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
                ", currentLocation=" + currentLocation +
                ", nameOfHolder='" + nameOfHolder + '\'' +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
