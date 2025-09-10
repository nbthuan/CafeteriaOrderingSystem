// LoginController.java
package edu.sundevil.cafeteria;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;


    @FXML
    private void handleLogin() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (user.equals("manager") && pass.equals("admin")) {
            MainApp.loadScene("menu_view.fxml", "Manager Dashboard");
        } else if (user.equals("customer") && pass.equals("cust123")) {
            MainApp.loadScene("customer_order.fxml", "Customer Order");
        } else if (user.equals("operator") && pass.equals("staff")) {
            MainApp.loadScene("operator_view.fxml", "Operator Dashboard");
        } else {
            messageLabel.setText("Invalid credentials. Try again.");
        }
    }
}

