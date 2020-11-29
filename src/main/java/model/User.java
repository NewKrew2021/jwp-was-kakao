package model;

import utils.MessageUtils;
import validator.InputValidator;

import java.util.Objects;

public class User {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(String userId, String password, String name, String email) {
        validate(userId);
        validate(password);
        validate(name);
        validate(email);
        validateEmail(email);

        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, name, email);
    }

    private static void validate(String value) {
        if (!InputValidator.isValidEmpty(value)) {
            throw new IllegalArgumentException(MessageUtils.INVALID_USER_PARAM);
        }
    }

    private static void validateEmail(String email) {
        if (!InputValidator.isValidEmail(email)) {
            throw new IllegalArgumentException(MessageUtils.INVALID_USER_EMAIL);
        }
    }
}
