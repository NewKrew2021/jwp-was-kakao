package webserver.http.controller;

import org.springframework.util.StringUtils;
import webserver.http.HttpRequestParam;

import java.util.List;
import java.util.stream.Collectors;

public class HttpRequestParamValidator {

    private List<String> requiredParams;

    public HttpRequestParamValidator(List<String> requiredParams) {
        this.requiredParams = requiredParams;
    }

    public void validate(List<HttpRequestParam> params) {
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
