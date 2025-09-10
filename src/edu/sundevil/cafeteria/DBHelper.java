package edu.sundevil.cafeteria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private static final String URL = "jdbc:sqlite:cafeteria.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void init() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            // Table for menu items (includes status + available)
            String itemSql = "CREATE TABLE IF NOT EXISTS items (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "menu TEXT," +
                    "name TEXT," +
                    "description TEXT," +
                    "status TEXT DEFAULT 'Preparing'," +
                    "available BOOLEAN DEFAULT 1)";
            stmt.execute(itemSql);

            // Table for orders - now includes timestamp
            String orderSql = "CREATE TABLE IF NOT EXISTS orders (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "customer TEXT," +
                    "itemName TEXT," +
                    "status TEXT," +
                    "timestamp TEXT)";
            stmt.execute(orderSql);

        } catch (SQLException e) {
            System.err.println("Database init error: " + e.getMessage());
        }
    }

    public static boolean itemExists(String menu, String name) {
        String sql = "SELECT COUNT(*) FROM items WHERE menu = ? AND name = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, menu);
            stmt.setString(2, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Check duplicate error: " + e.getMessage());
        }
        return false;
    }

    public static void saveItem(Item item) {
        String sql = "INSERT INTO items (menu, name, description, status, available) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getMenu());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getDescription());
            stmt.setString(4, item.getStatus());
            stmt.setBoolean(5, item.isAvailable());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Insert item error: " + e.getMessage());
        }
    }

    public static List<Item> loadAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                items.add(new Item(
                        rs.getString("menu"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getBoolean("available")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Load items error: " + e.getMessage());
        }
        return items;
    }

    // ✅ Save order with timestamp
    public static void saveOrder(String customer, String itemName, String timestamp) {
        String sql = "INSERT INTO orders (customer, itemName, status, timestamp) VALUES (?, ?, 'Preparing', ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer);
            stmt.setString(2, itemName);
            stmt.setString(3, timestamp);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Insert order error: " + e.getMessage());
        }
    }

    public static List<Order> loadAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(new Order(
                        rs.getString("customer"),
                        rs.getString("itemName"),
                        rs.getString("status"),
                        rs.getString("timestamp")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Load orders error: " + e.getMessage());
        }
        return orders;
    }

    public static List<Order> loadOrdersByCustomer(String customer) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getString("customer"),
                        rs.getString("itemName"),
                        rs.getString("status"),
                        rs.getString("timestamp")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Load customer orders error: " + e.getMessage());
        }
        return orders;
    }

    public static List<Order> loadOrdersByStatus(String status) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getString("customer"),
                        rs.getString("itemName"),
                        rs.getString("status"),
                        rs.getString("timestamp")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Load filtered orders error: " + e.getMessage());
        }
        return orders;
    }

    public static void updateItemStatus(String menu, String name, String newStatus) {
        String sql = "UPDATE items SET status = ? WHERE menu = ? AND name = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setString(2, menu);
            stmt.setString(3, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Update item status error: " + e.getMessage());
        }
    }

    public static void updateItemAvailability(String menu, String name, boolean available) {
        String sql = "UPDATE items SET available = ? WHERE menu = ? AND name = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, available);
            stmt.setString(2, menu);
            stmt.setString(3, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Update availability error: " + e.getMessage());
        }
    }

    public static void updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Update order status error: " + e.getMessage());
        }
    }

    public static void updateOrderStatusByCustomerItem(String customer, String itemName, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE customer = ? AND itemName = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setString(2, customer);
            stmt.setString(3, itemName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Update order status error: " + e.getMessage());
        }
    }
}
