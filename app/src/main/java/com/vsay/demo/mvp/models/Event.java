package com.vsay.demo.mvp.models;

/**
 * Created by datct0407 on 10/6/15.
 */
public class Event {

    private String id;
    private String description;
    private String title;
    private String timestamp;
    private String image;
    private String phone;
    private String date;
    private String locationline1;
    private String locationline2;

    public Event(String id, String description, String title, String timestamp, String image, String phone, String date, String locationline1, String locationline2) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.timestamp = timestamp;
        this.image = image;
        this.phone = phone;
        this.date = date;
        this.locationline1 = locationline1;
        this.locationline2 = locationline2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocationline1() {
        return locationline1;
    }

    public void setLocationline1(String locationline1) {
        this.locationline1 = locationline1;
    }

    public String getLocationline2() {
        return locationline2;
    }

    public void setLocationline2(String locationline2) {
        this.locationline2 = locationline2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}