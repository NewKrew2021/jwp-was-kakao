package webserver.http.controller;

import webserver.http.HttpRequestParam;

import java.util.Arrays;
import java.util.List;

public class Login implements RequestDataObject {

    private String userId;
    private String password;

    public Login(List<HttpRequestParam> params) {
        validate(params);

        userId = getValue(params, "userId");
        password = getValue(params, "password");
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public List<RequestDataObjectValidator> getValidators() {
        return Arrays.asList(new RequiredParamConstraint("userId","password"));
    }
}
