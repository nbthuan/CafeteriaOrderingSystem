package edu.sundevil.cafeteria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class OperatorController {
    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, String> customerCol;
    @FXML private TableColumn<Order, String> itemCol;
    @FXML private TableColumn<Order, String> statusCol;
    @FXML private TableColumn<Order, String> timeCol;
    @FXML private ComboBox<String> statusFilterBox;
    @FXML private Label messageLabel;

    private final ObservableList<Order> orderList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        itemCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        orderTable.setItems(orderList);

        statusFilterBox.setItems(FXCollections.observableArrayList("All", "Preparing", "Ready"));
        statusFilterBox.getSelectionModel().select("All");

        loadOrders("All");
    }

    private void loadOrders(String status) {
        List<Order> orders = DBHelper.loadAllOrders();
        orderList.clear();

        for (Order o : orders) {
            if ("All".equals(status) || o.getStatus().equalsIgnoreCase(status)) {
                orderList.add(o);
            }
        }
    }

    @FXML
    public void handleFilterChange() {
        String selected = statusFilterBox.getSelectionModel().getSelectedItem();
        loadOrders(selected);
    }

    @FXML
    public void handleMarkReady() {
        Order selected = orderTable.getSelectionModel().getSelectedItem();
        if (selected != null && !"Ready".equalsIgnoreCase(selected.getStatus())) {
            DBHelper.updateOrderStatusByCustomerItem(selected.getCustomer(), selected.getItemName(), "Ready");
            messageLabel.setText("Marked as Ready: " + selected.getItemName());
            loadOrders(statusFilterBox.getSelectionModel().getSelectedItem());
        } else {
            messageLabel.setText("Select an order that is not already Ready.");
        }
    }
}
