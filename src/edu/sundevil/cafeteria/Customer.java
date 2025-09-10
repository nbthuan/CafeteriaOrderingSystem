package edu.sundevil.cafeteria;

public class Customer extends User {

    public Customer(String userID, String password) {
        super(userID, password, "customer");
    }

    // Example order placement with fixed item for testing/demo
    public Order placeOrder() {
        return new Order(getUserID(), "Burger");  // 👈 Fixed: 2 arguments
    }
}
