/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.entity;

public class PictureData {
    private String name = "";
    private String type = "";
    private int size = 0;
    private long lastModified = 0;
    private String lastModifiedDate = "";

    public PictureData() {
    }

    public PictureData(String name, String type, int size, long lastModified, String lastModifiedDate) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.lastModified = lastModified;
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    @Override
    public String toString() {
        return "Picture{" +
                "name='" + name + '\'' +
                ", typeData='" + type + '\'' +
                ", size=" + size +
                ", lastModified=" + lastModified +
                ", lastModifiedDate='" + lastModifiedDate + '\'' +
                '}';
    }
}
