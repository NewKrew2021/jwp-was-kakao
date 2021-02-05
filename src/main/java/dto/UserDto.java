package dto;

import request.HttpRequest;

public class UserDto {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private String userId;
    private String password;
    private String name;
    private String email;

    private UserDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static UserDto from(HttpRequest httpRequest) {
        return new UserDto(httpRequest.getParameter(USER_ID), httpRequest.getParameter(PASSWORD),
                httpRequest.getParameter(NAME), httpRequest.getParameter(EMAIL));
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
