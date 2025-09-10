package edu.sundevil.cafeteria;

import java.util.ArrayList;
import java.util.List;

public class Login {
    private static Login instance;
    private List<User> users = new ArrayList<>();
    private User activeUser;

    private Login() {}

    public static Login getInstance() {
        if (instance == null) instance = new Login();
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean authenticate(String id, String password) {
        for (User u : users) {
            if (u.getUserID().equals(id)) {
                activeUser = u;
                return true;
            }
        }
        return false;
    }

    public User getActiveUser() { return activeUser; }
}
