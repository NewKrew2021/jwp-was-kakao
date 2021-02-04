package model;

import exception.user.WrongEmailException;
import exception.user.WrongIdException;
import exception.user.WrongNameException;
import exception.user.WrongPasswordException;

public class User {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(String userId, String password, String name, String email) {
        this.userId = validateId(userId);
        this.password = validatePw(password);
        this.name = validateName(name);
        this.email = validateEmail(email.replace("%40", "@"));
    }

    private String validatePw(String password) {
        if (!password.matches("^[\\w]{1,30}$")) {
            throw new WrongPasswordException();
        }
        return password;
    }

    private String validateId(String userId) {
        if (!userId.matches("^[\\w]{1,30}$"))
            throw new WrongIdException();
        return userId;
    }

    private String validateName(String name) {
        if (!name.matches("^[\\w가-힣]{1,30}$")) {
            throw new WrongNameException();
        }
        return name;
    }

    private String validateEmail(String email) {
        if (!email.matches("^[\\w]{1,30}@[\\w]{1,30}.[\\w]{1,30}$")) {
            throw new WrongEmailException();
        }
        return email;
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
