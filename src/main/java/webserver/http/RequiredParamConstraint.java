package webserver.http;

import utils.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RequiredParamConstraint implements HttpRequestDataObjectValidator {

    private List<String> requiredParams;

    public RequiredParamConstraint(String... requiredParams) {
        this(Arrays.asList(requiredParams));
    }

    public RequiredParamConstraint(List<String> requiredParams) {
        this.requiredParams = requiredParams;
    }

    @Override
    public void validate(List<HttpRequestParam> params) {
        List<String> inputParams = params.stream()
                .map(HttpRequestParam::getName)
                .collect(Collectors.toList());

        shouldHaveRequiredParams(inputParams);
        shouldHaveValue(params, requiredParams);
    }

    private void shouldHaveValue(List<HttpRequestParam> params, List<String> notEmptyParams) {
        params.forEach(it -> {
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

