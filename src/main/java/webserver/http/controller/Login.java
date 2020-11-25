package webserver.http.controller;

import webserver.http.HttpRequestParam;

import java.util.Arrays;
import java.util.List;

public class Login implements RequestDataObject {
    private final List<String> requiredParams = Arrays.asList("userId","password");
    private HttpRequestParamValidator paramValidator = new HttpRequestParamValidator(requiredParams);

    private String userId;
    private String password;

    public Login(List<HttpRequestParam> params) {
        paramValidator.validate(params);

        userId = getValue(params, "userId");
        password = getValue(params, "password");
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
