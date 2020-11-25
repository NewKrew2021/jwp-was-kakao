package webserver.http.controller;

import db.DataBase;
import model.User;
import webserver.http.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoginController implements Controller{

    private final List<String> requiredParams = Arrays.asList("userId","password");

    private HttpRequestParamValidator paramValidator = new HttpRequestParamValidator(requiredParams);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<HttpRequestParam> params = HttpRequestParams.convertFrom(httpRequest.getBody());
        paramValidator.validate(params);

        Map<String, String> paramMap = params.stream()
                .collect(Collectors.toMap(HttpRequestParam::getName, HttpRequestParam::getValue));

        User found = DataBase.findUserById(paramMap.get("userId"));
        if( found != null ){
            httpResponse.setStatus(HttpStatus.x302_Found);
            httpResponse.addHeader("Location", "/index.html");
        } else {
            httpResponse.setStatus(HttpStatus.x302_Found);
            httpResponse.addHeader("Location", "/user/login_failed.html");
        }
    }

}
