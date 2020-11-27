package webserver.http.controller;

import webserver.http.HttpRequestParam;

import java.util.Arrays;
import java.util.List;

public class SignUp implements RequestDataObject {

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
    public List<RequestDataObjectValidator> getValidators() {
        return Arrays.asList(new RequiredParamConstraint("userId", "password", "name", "email"));
    }
}
