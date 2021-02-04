package user.model;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import user.exceptions.IllegalUserValuesException;

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

        checkIsValidUser();
    }

    public boolean same(String password) {
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

    private void checkIsValidUser() {
        if (StringUtils.isBlank(userId)) {
            throw new IllegalUserValuesException("Please check userId");
        }

        if (StringUtils.isBlank(password)) {
            throw new IllegalUserValuesException("Please check password");
        }

        if (StringUtils.isBlank(name)) {
            throw new IllegalUserValuesException("Please check name");
        }

        if (!isValidEmail()) {
            throw new IllegalUserValuesException("Please check email");
        }
    }

    private boolean isValidEmail() {
        String pattern = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        return !email.matches(pattern);
    }
}
