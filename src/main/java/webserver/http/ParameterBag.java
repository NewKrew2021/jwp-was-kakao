package webserver.http;

import webserver.http.exceptions.HttpParamException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class ParameterBag {

    public static final String DEFAULT_CHARACTER_ENCODING = "utf-8";
    private final Map<String, String> parameters;

    public ParameterBag(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getParameter(String key) {
        String value = parameters.get(key);

        try {
            return URLDecoder.decode(value, DEFAULT_CHARACTER_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new HttpParamException("KEY:[" + key + "] 파라미터 디코딩에 문제가 발생했습니다.", e);
        }
    }

    public boolean hasParameter(String key) {
        return parameters.containsKey(key);
    }
}
