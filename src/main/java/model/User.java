package model;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {

    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User of(String userId, String password) {
        return new User(userId, password, null, null);
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

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }

    public boolean isFilledCreateForm() {
        if(userId == null || password == null || name == null || email == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return " { \"userId\" : \"" + userId + "\", \"password\" :\"" + password + "\", \"name\":\"" + name + "\", \"email\":\"" + email + "\"}";
    }
}
