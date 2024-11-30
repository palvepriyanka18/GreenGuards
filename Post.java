package com.greenguards.components;

public class Post {
    private int id;
    private String description;
    private int quantity;
    private String tag;
    private byte[] image;  // Change to byte array for storing image data

    // Constructor for creating a new post (without ID)
    public Post(String description, int quantity, String tag, byte[] image) {
        this.description = description;
        this.quantity = quantity;
        this.tag = tag;
        this.image = image;
    }

    // Constructor to create Post from database result (with ID)
    public Post(int id, String description, int quantity, String tag, byte[] image) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.tag = tag;
        this.image = image;
    }

    // Getters and setters for the attributes
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
