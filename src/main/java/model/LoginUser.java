package model;

import java.util.Map;

public class LoginUser {
    private String userId;
    private String password;

    private LoginUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public static LoginUser of(Map<String, String> parameter) {
        return new LoginUser(parameter.get("userId"), parameter.get("password"));
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
