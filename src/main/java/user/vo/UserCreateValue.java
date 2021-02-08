package user.vo;

import webserver.http.HttpRequest;

public class UserCreateValue {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public UserCreateValue(HttpRequest httpRequest) {
        this.userId = httpRequest.getParameter("userId");
        this.password = httpRequest.getParameter("password");
        this.name = httpRequest.getParameter("name");
        this.email = httpRequest.getParameter("email");
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
