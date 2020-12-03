package apps.slipp.controller;

import model.User;
import webserver.http.HttpRequestParam;
import webserver.http.HttpRequestDataObject;
import webserver.http.HttpRequestDataObjectValidator;
import webserver.http.RequiredParamConstraint;

import java.util.Arrays;
import java.util.List;

public class SignUp implements HttpRequestDataObject {

    private String userId;
    private String password;
    private String name;
    private String email;

    public SignUp(List<HttpRequestParam> params) {
        validate(params);

        userId = getValue(params, "userId");
        password = getValue(params, "password");
        name = getValue(params, "name");
        email = getValue(params, "email");
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
    public List<HttpRequestDataObjectValidator> getValidators() {
        return Arrays.asList(new RequiredParamConstraint("userId", "password", "name", "email"));
    }

    public User toUserModel() {
        return new User(userId, password, name, email);
    }
}
