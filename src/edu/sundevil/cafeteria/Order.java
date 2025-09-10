package edu.sundevil.cafeteria;

public class Order {
    private String customer;
    private String itemName;
    private String status;
    private String timestamp; // NEW FIELD

    // Existing constructor
    public Order(String customer, String itemName) {
        this.customer = customer;
        this.itemName = itemName;
        this.status = "Preparing";
        this.timestamp = "";
    }

    // ✅ NEW constructor with timestamp
    public Order(String customer, String itemName, String status, String timestamp) {
        this.customer = customer;
        this.itemName = itemName;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getCustomer() { return customer; }
    public String getItemName() { return itemName; }
    public String getStatus() { return status; }
    public String getTimestamp() { return timestamp; }

    public void setCustomer(String customer) { this.customer = customer; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setStatus(String status) { this.status = status; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
