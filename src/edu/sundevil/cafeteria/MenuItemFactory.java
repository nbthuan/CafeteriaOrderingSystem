package edu.sundevil.cafeteria;

public class MenuItemFactory {
    public static Item createItem(String menu, String name, String desc) {
        return new Item(menu, name, desc);
    }
}
