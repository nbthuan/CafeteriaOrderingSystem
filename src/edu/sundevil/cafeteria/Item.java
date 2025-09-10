package edu.sundevil.cafeteria;

public class Item {
    private String menu;
    private String name;
    private String description;
    private String status;       // Order status: "Preparing", "Ready", etc.
    private boolean available;   // True if item is available to order

    // --- Constructors ---

    // Constructor for adding new item (default: Preparing + available)
    public Item(String menu, String name, String description) {
        this.menu = menu;
        this.name = name;
        this.description = description;
        this.status = "Preparing";
        this.available = true;
    }

    // Constructor used when reading from DB with full data
    public Item(String menu, String name, String description, String status, boolean available) {
        this.menu = menu;
        this.name = name;
        this.description = description;
        this.status = status;
        this.available = available;
    }

    // --- Getters and Setters ---

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
