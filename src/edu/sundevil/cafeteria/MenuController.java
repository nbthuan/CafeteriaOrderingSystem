package edu.sundevil.cafeteria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class MenuController {
    @FXML private TextField menuField;
    @FXML private TextField nameField;
    @FXML private TextField descField;
    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> menuCol;
    @FXML private TableColumn<Item, String> nameCol;
    @FXML private TableColumn<Item, String> descCol;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;

    private ObservableList<Item> itemList = FXCollections.observableArrayList();
    private Item selectedItem = null;

    @FXML
    private void initialize() {
        menuCol.setCellValueFactory(new PropertyValueFactory<>("menu"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableView.setItems(itemList);
        loadItemsFromDB();

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedItem = newSel;
                menuField.setText(newSel.getMenu());
                nameField.setText(newSel.getName());
                descField.setText(newSel.getDescription());
            }
        });
    }

    @FXML
    private void onAddItem() {
        String menu = menuField.getText();
        String name = nameField.getText();
        String desc = descField.getText();

        if (!menu.isEmpty() && !name.isEmpty()) {
            String sql = "INSERT INTO items(menu, name, description) VALUES (?, ?, ?)";
            try (Connection conn = DBHelper.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, menu);
                stmt.setString(2, name);
                stmt.setString(3, desc);
                stmt.executeUpdate();
                loadItemsFromDB();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onDeleteItem() {
        if (selectedItem == null) return;

        String sql = "DELETE FROM items WHERE menu = ? AND name = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, selectedItem.getMenu());
            stmt.setString(2, selectedItem.getName());
            stmt.executeUpdate();
            loadItemsFromDB();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onUpdateItem() {
        if (selectedItem == null) return;

        String updatedMenu = menuField.getText();
        String updatedName = nameField.getText();
        String updatedDesc = descField.getText();

        String sql = "UPDATE items SET menu = ?, name = ?, description = ? WHERE menu = ? AND name = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, updatedMenu);
            stmt.setString(2, updatedName);
            stmt.setString(3, updatedDesc);
            stmt.setString(4, selectedItem.getMenu());
            stmt.setString(5, selectedItem.getName());
            stmt.executeUpdate();
            loadItemsFromDB();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClearFields() {
        clearFields();
    }

    private void clearFields() {
        menuField.clear();
        nameField.clear();
        descField.clear();
        selectedItem = null;
        tableView.getSelectionModel().clearSelection();
    }

    private void loadItemsFromDB() {
        itemList.clear();
        String sql = "SELECT * FROM items";
        try (Connection conn = DBHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                itemList.add(new Item(
                        rs.getString("menu"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
