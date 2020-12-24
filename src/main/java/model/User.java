package model;

import webserver.http.ParameterBag;

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
    }

    public static User createUser(ParameterBag params) {

        validateUserParam(params);

        String userId = params.getParameter("userId");
        String password = params.getParameter("password");
        String name = params.getParameter("name");
        String email = params.getParameter("email");

        return new User(userId, password, name, email);
    }

    private static void validateUserParam(ParameterBag params) {

        if( !params.hasParameter("userId")
                ||
            !params.hasParameter("password")
                ||
            !params.hasParameter("name")
                ||
            !params.hasParameter("email")
        ) {
            throw new IllegalStateException();
        }

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
}
