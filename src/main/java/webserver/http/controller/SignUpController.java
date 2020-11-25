package webserver.http.controller;

import db.DataBase;
import model.User;
import org.springframework.util.StringUtils;
import webserver.http.*;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SignUpController implements Controller {

    private final List<String> requiredParams = Arrays.asList("userId", "password", "name", "email");

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<HttpRequestParam> params = HttpRequestParams.convertFrom(httpRequest.getBody());
        validateParams(params);

        Map<String, String> paramMap = params.stream()
                .collect(Collectors.toMap(HttpRequestParam::getName, HttpRequestParam::getValue));

        User newUser = new User(
                paramMap.get("userId"),
                paramMap.get("password"),
                paramMap.get("name"),
                paramMap.get("email")
        );
        DataBase.addUser(newUser);

        httpResponse.setStatusLine(MessageFormat.format("HTTP/1.1 {0} {1}", HttpStatus.x201_Created.getStatusCode(), HttpStatus.x201_Created.getReasonPhrase()));
    }

    private void validateParams(List<HttpRequestParam> params) {
        List<String> inputParams = params.stream()
                .map(HttpRequestParam::getName)
                .collect(Collectors.toList());

        shouldHaveRequiredParams(inputParams);
        shouldHaveValue(params, requiredParams);
    }

    private void shouldHaveValue(List<HttpRequestParam> params, List<String> notEmptyParams) {
        params.stream().forEach(it -> {
            if (notEmptyParams.contains(it.getName()) && StringUtils.isEmpty(it.getValue()))
                throw new NotEmptyParamException(it.getName());
        });

    }

    private void shouldHaveRequiredParams(List<String> inputParams) {
        requiredParams.forEach(required -> {
            if (!inputParams.contains(required)) throw new MissingRequiredParamException(required);
        });
    }

}
