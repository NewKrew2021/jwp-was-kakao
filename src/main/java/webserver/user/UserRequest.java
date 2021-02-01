package webserver.user;

import model.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UserRequest {
    public static final int VALUE_INDEX = 1;
    public static final int USER_ID_INDEX = 0;
    public static final int PASSWORD_INDEX = 1;
    public static final int NAME_INDEX = 2;
    public static final int EMAIL_INDEX = 3;

    private String userId;
    private String password;
    private String name;
    private String email;


    private UserRequest(String userRequestParam) {
        String[] tokens = userRequestParam.split("&");
        this.userId = tokens[USER_ID_INDEX].split("=")[VALUE_INDEX];
        this.password = tokens[PASSWORD_INDEX].split("=")[VALUE_INDEX];
        try {
            this.name = URLDecoder.decode(tokens[NAME_INDEX].split("=")[VALUE_INDEX],
                    java.nio.charset.StandardCharsets.UTF_8.toString());
            this.email = URLDecoder.decode(tokens[EMAIL_INDEX].split("=")[VALUE_INDEX],
                    java.nio.charset.StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static UserRequest of(String userRequestParam) {
        return new UserRequest(userRequestParam);
    }

    public User toUser() {
        return User.of(userId, password, name, email);
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
}
