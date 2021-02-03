package user.model;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;

        checkIsValidUser();
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

    private void checkIsValidUser() {
        if (StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException("Please check userId");
        }

        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Please check password");
        }

        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Please check name");
        }

        if (isInvalidEmail()) {
            throw new IllegalArgumentException("Please check email");
        }
    }

    private boolean isInvalidEmail() {
        String pattern = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        return !email.matches(pattern);
    }

    public boolean same(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
