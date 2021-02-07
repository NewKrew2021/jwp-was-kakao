package model;

import java.util.Map;
import java.util.Objects;

public class User {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(String userId, String password, String name, String email) {
        validate(userId, password, name, email);
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    private void validate(String userId, String password, String name, String email) {
        if (isEmpty(userId)) {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        }
        if (isEmpty(password)) {
            throw new IllegalArgumentException("비밀번호가 존재하지 않습니다.");
        }
        if (isEmpty(name)) {
            throw new IllegalArgumentException("이름이 존재하지 않습니다.");
        }
        if (isEmpty(email)) {
            throw new IllegalArgumentException("이메일이 존재하지 않습니다.");
        }
    }

    public static User of(String userId, String password, String name, String email) {
        return new User(userId, password, name, email);
    }

    public static User from(Map<String, String> params) {
        return new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
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
        return userId.equals(user.userId) && password.equals(user.password) && name.equals(user.name) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, name, email);
    }
}
