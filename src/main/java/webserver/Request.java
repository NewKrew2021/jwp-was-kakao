package webserver;

import dto.ParamValue;
import dto.RequestValue;
import utils.MessageUtils;

import java.util.Optional;

public class Request {

    private final String path;
    private final String method;
    private final Optional<ParamValue> paramMap;

    private Request(String path, String method, Optional<ParamValue> paramMap) {
        this.path = path;
        this.method = method;
        this.paramMap = paramMap;
    }

    public static Request of(RequestValue requestValue) {
        String url = requestValue.getURL();
        String method = requestValue.getMethod();
        Optional<ParamValue> param = ParamValue.of(requestValue.getParams());

        return new Request(url, method, param);
    }

    public String getURLPath() {
        return this.path;
    }

    public ParamValue getParamMap() {
        if (this.paramMap.isPresent()) {
            return paramMap.get();
        }
        throw new IllegalStateException(MessageUtils.PARAM_VALUE_IS_EMPTY);
    }
}
