package model;

import java.util.Objects;

public class User {

    private static final String NOT_EXIST_USERID_MESSAGE = "아이디가 입력되지 않았습니다.";
    private static final String NOT_EXIST_PASSWORD_MESSAGE = "비밀번호가 입력되지 않았습니다.";
    private static final String NOT_EXIST_NAME_MESSAGE = "이름이 입력되지 않았습니다.";
    private static final String NOT_EXIST_EMAIL_MESSAGE = "이메일이 입력되지 않았습니다.";

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(String userId, String password, String name, String email) {
        validateUserArgument(userId, password, name, email);
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    private void validateUserArgument(String userId, String password, String name, String email) {
        if (isEmpty(userId)) {
            throw new IllegalArgumentException(NOT_EXIST_USERID_MESSAGE);
        }
        if (isEmpty(password)) {
            throw new IllegalArgumentException(NOT_EXIST_PASSWORD_MESSAGE);
        }
        if (isEmpty(name)) {
            throw new IllegalArgumentException(NOT_EXIST_NAME_MESSAGE);
        }
        if (isEmpty(email)) {
            throw new IllegalArgumentException(NOT_EXIST_EMAIL_MESSAGE);
        }
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static User of(String userId, String password, String name, String email) {
        return new User(userId, password, name, email);
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

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
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
        return userId.equals(user.userId) && password.equals(user.password) && name.equals(user.name) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, name, email);
    }
}
