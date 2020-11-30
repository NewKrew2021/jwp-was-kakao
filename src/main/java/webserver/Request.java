package webserver;

import dto.ParamValue;
import dto.RequestValue;
import utils.MessageUtils;

import java.util.Optional;

public class Request {

    private final String urlPath;
    private final String method;
    private final Optional<ParamValue> paramMap;

    private Request(String urlPath, String method, Optional<ParamValue> paramMap) {
        this.urlPath = urlPath;
        this.method = method;
        this.paramMap = paramMap;
    }

    public static Request of(RequestValue requestValue) {
        String urlPath = requestValue.getURLPath();
        String method = requestValue.getMethod();
        Optional<ParamValue> param = ParamValue.of(requestValue.getParams());

        return new Request(urlPath, method, param);
    }

    public String getURLPath() {
        return this.urlPath;
    }

    public ParamValue getParamMap() {
        if (this.paramMap.isPresent()) {
            return paramMap.get();
        }
        throw new IllegalStateException(MessageUtils.PARAM_VALUE_IS_EMPTY);
    }
}
