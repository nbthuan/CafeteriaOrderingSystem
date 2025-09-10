package edu.sundevil.cafeteria;

public class CafeteriaManager extends User {

    public CafeteriaManager(String userID, String password) {
        super(userID, password, "Manager");
    }

    public Item addItem(String menu, String name, String desc) {
        return MenuItemFactory.createItem(menu, name, desc);
    }

    public void removeItem(Item item) {
        item.setAvailable(false);
    }
}
