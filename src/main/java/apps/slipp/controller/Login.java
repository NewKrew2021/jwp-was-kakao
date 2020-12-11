package apps.slipp.controller;

import webserver.http.HttpRequestDataObject;
import webserver.http.HttpRequestDataObjectValidator;
import webserver.http.HttpRequestParam;
import webserver.http.RequiredParamConstraint;

import java.util.Arrays;
import java.util.List;

public class Login implements HttpRequestDataObject {

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
    public List<HttpRequestDataObjectValidator> getValidators() {
        return Arrays.asList(new RequiredParamConstraint("userId", "password"));
    }
}
