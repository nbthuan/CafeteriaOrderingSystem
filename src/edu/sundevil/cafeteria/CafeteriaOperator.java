package edu.sundevil.cafeteria;

public class CafeteriaOperator extends User {

    public CafeteriaOperator(String userID, String password) {
        super(userID, password, "Operator");
    }

    public void updateOrderStatus(Order order, String status) {
        order.setStatus(status);
    }
}
