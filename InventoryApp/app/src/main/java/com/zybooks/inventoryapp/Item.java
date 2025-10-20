package com.zybooks.inventoryapp;

public class Item {
    private int id;  // Add an ID for each item
    private String name;
    private int quantity;
    private String imagePath;

    // Constructor
    public Item(int id, String name, int quantity, String imagePath) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImagePath() {
        return imagePath;
    }
}
