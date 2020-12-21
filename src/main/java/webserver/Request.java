package webserver;

import dto.ParamValue;
import dto.RequestValue;
import utils.DecodeUtils;
import validator.InputValidator;

import java.util.Map;
import java.util.Optional;

public class Request {

    private static final String QUESTION_MARK = "?";
    private static final String REGEX_QUESTION_MARK = "\\?";
    private static final String REGEX_AMPERSAND = "&";

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
        Optional<String> getParams = getGetParams();
        Optional<String> postParams = getPostParams();

        if (getParams.isPresent() && postParams.isPresent()) {
            String params = getParams.get() + REGEX_AMPERSAND + postParams.get();
            return Optional.of(params);

        } else if (postParams.isPresent()) {
            return postParams;

        } else if (getParams.isPresent()) {
            return getParams;
        }

        return Optional.empty();
    }

    private Optional<String> getGetParams() {
        if (!requestHeader.getURL().contains(QUESTION_MARK)) {
            return Optional.empty();
        }
        return Optional.of(DecodeUtils.decodeUTF8(requestHeader.getParamString()));
    }

    private Optional<String> getPostParams() {
        if (InputValidator.isValidEmpty(requestBody.getBody())) {
            return Optional.of(DecodeUtils.decodeUTF8(requestBody.getBody()));
        }
        return Optional.empty();
    }

    public String getMethod() {
        return requestHeader.getMethod();
    }

    public boolean isGetMethod() {
        return requestHeader.getMethod().equals(GET_METHOD);
    }

    public boolean isPostMethod() {
        return requestHeader.getMethod().equals(POST_METHOD);
    }

    public String getURL() {
        return requestHeader.getURL();
    }

    public String getPathGateway() {
        return requestHeader.getPathGateway();
    }

    public Map<String, String> getHeader() {
        return requestHeader.getHeader();
    }

    public String getHeader(String key) {
        return requestHeader.getHeader(key);
    }


    public boolean isLogined() {
        return requestHeader.isLogined();
    }
}
