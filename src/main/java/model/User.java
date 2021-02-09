package model;

import utils.KeyValueTokenizer;

import java.util.Map;

public class User {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User(Map<String, String> parameters) {
        this.userId = parameters.get("userId");
        this.password = parameters.get("password");
        this.name = parameters.get("name");
        this.email = parameters.get("email");
    }

    public static User of(String input) {
        Map<String, String> parameters = KeyValueTokenizer.of(input);
        return new User(parameters);
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
