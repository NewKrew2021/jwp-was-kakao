package webserver;

import dto.ParamValue;
import dto.RequestValue;
import utils.DecodeUtils;
import validator.InputValidator;

import java.util.Optional;

public class Request {

    private static final String QUESTION_MARK = "?";
    private static final String REGEX_QUESTION_MARK = "\\?";

    private static final String POST_METHOD = "POST";
    private static final String GET_METHOD = "GET";

    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    private Request(RequestHeader requestHeader, RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static Request of(RequestValue requestValue) {
        RequestHeader requestHeader = RequestHeader.of(requestValue);
        RequestBody requestBody = RequestBody.of(requestValue);

        return new Request(requestHeader, requestBody);
    }

    public Optional<ParamValue> getParamMap() {
        return ParamValue.of(getParams());
    }

    private Optional<String> getParams() {
        switch (requestHeader.getMethod()) {
            case POST_METHOD:
                return getPostParams();
            case GET_METHOD:
                return getGetParams();
        }
        return Optional.empty();
    }

    private Optional<String> getGetParams() {
        if (!requestHeader.getURL().contains(QUESTION_MARK)) {
            return Optional.empty();
        }
        String paramString = requestHeader.getURL().split(REGEX_QUESTION_MARK)[1];
        return Optional.of(DecodeUtils.decodeUTF8(paramString));
    }

    private Optional<String> getPostParams() {
        if (InputValidator.isValidEmpty(requestBody.getBody())) {
            return Optional.of(DecodeUtils.decodeUTF8(requestBody.getBody()));
        }
        return Optional.empty();
    }

    public String getURL() {
        return requestHeader.getURL();
    }

    public String getPathGateway() {
        return requestHeader.getPathGateway();
    }


    public boolean isLogined() {
        return requestHeader.isLogined();
    }
}
