package edu.sundevil.cafeteria;

public class User {
    protected String userID;
    protected String password;
    protected String role;

    public User(String userID, String password, String role) {
        this.userID = userID;
        this.password = password;
        this.role = role;
    }

    public String getUserID() { return userID; }
    public String getRole() { return role; }
}
