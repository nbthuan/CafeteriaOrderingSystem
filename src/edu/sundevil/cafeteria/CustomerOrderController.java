package edu.sundevil.cafeteria;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomerOrderController {

    @FXML private TextField customerField;
    @FXML private ComboBox<String> menuBox;
    @FXML private ListView<String> itemListView;
    @FXML private Label statusLabel;

    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, String> customerCol;
    @FXML private TableColumn<Order, String> itemCol;
    @FXML private TableColumn<Order, String> statusCol;

    private final ObservableList<Order> orderList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup menu options
        menuBox.setItems(FXCollections.observableArrayList("Breakfast", "Lunch", "Dinner", "Drinks"));
        menuBox.setOnAction(e -> loadItems(menuBox.getValue()));

        // Allow multiple selections in the list
        itemListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Setup table columns
        customerCol.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getCustomer()));
        itemCol.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getItemName()));
        statusCol.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getStatus()));

        orderTable.setItems(orderList);
        loadOrders();
    }

    private void loadItems(String menu) {
        itemListView.getItems().clear();
        List<Item> items = DBHelper.loadAllItems();
        for (Item item : items) {
            if (item.getMenu().equals(menu) && item.isAvailable()) {
                itemListView.getItems().add(item.getName());
            }
        }
    }

    @FXML
    private void handleSubmitOrder() {
        String customer = customerField.getText().trim();
        List<String> selectedItems = itemListView.getSelectionModel().getSelectedItems();

        if (customer.isEmpty() || selectedItems.isEmpty()) {
            statusLabel.setText("Please enter your name and select item(s).");
            return;
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        for (String item : selectedItems) {
            DBHelper.saveOrder(customer, item, timestamp);
        }

        statusLabel.setText("Order submitted.");
        loadOrders();
        clearFields();
    }

    private void loadOrders() {
        orderList.setAll(DBHelper.loadAllOrders());
    }

    private void clearFields() {
        customerField.clear();
        menuBox.getSelectionModel().clearSelection();
        itemListView.getItems().clear();
    }
}
