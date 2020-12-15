package model.user;

import java.util.Objects;

public class User {
    private static final int USER_ID_MIN_LENGTH = 1;
    private static final int PASSWORD_MIN_LENGTH = 1;
    private static final int NAME_MIN_LENGTH = 1;
    private static final int EMAIL_MIN_LENGTH = 1;

    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        validateUserId(userId);
        validatePassword(password);
        validateName(name);
        validateEmail(email);
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    private void validateUserId(String userId) {
        if (userId.length() < USER_ID_MIN_LENGTH) {
            throw new InvalidUserIdException();
        }
    }

    private void validatePassword(String password) {
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new InvalidPasswordException();
        }
    }

    private void validateName(String name) {
        if (name.length() < NAME_MIN_LENGTH) {
            throw new InvalidNameException();
        }
    }

    private void validateEmail(String email) {
        if (email.length() < EMAIL_MIN_LENGTH) {
            throw new InvalidEmailException();
        }
    }

    public static User nobody() {
        return new User("null", "null", "null", "null@null.com");
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

        if (!Objects.equals(userId, user.userId)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(name, user.name)) return false;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
