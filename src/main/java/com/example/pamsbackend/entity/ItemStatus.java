/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.entity;

public class ItemStatus {

    private Address currentLocation;
    private String nameOfHolder;
    private String purpose;
    private boolean customLocation = false;
    private float customLat;
    private float customLong;

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

    public boolean isCustomLocation() {
        return customLocation;
    }

    public void setCustomLocation(boolean customLocation) {
        this.customLocation = customLocation;
    }

    public float getCustomLat() {
        return customLat;
    }

    public void setCustomLat(float customLat) {
        this.customLat = customLat;
    }

    public float getCustomLong() {
        return customLong;
    }

    public void setCustomLong(float customLong) {
        this.customLong = customLong;
    }

    @Override
    public String toString() {
        return "ItemStatus{" +
                "currentLocation=" + currentLocation +
                ", nameOfHolder='" + nameOfHolder + '\'' +
                ", purpose='" + purpose + '\'' +
                ", customLocation=" + customLocation +
                ", customLat=" + customLat +
                ", customLong=" + customLong +
                '}';
    }
}
