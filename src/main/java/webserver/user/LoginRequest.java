package webserver.user;

import model.LoginUser;

public class LoginRequest {
    public static final int VALUE_INDEX = 1;
    public static final int USER_ID_INDEX = 0;
    public static final int PASSWORD_INDEX = 1;
    public static final int NAME_INDEX = 2;
    public static final int EMAIL_INDEX = 3;

    private String userId;
    private String password;


    private LoginRequest(String userRequestParam) {
        String[] tokens = userRequestParam.split("&");
        this.userId = tokens[USER_ID_INDEX].split("=")[VALUE_INDEX];
        this.password = tokens[PASSWORD_INDEX].split("=")[VALUE_INDEX];
    }

    public static LoginRequest of(String userRequestParam) {
        return new LoginRequest(userRequestParam);
    }

    public LoginUser toLoginUser() {
        return LoginUser.of(userId, password);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
