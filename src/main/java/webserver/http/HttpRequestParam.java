package webserver.http;

import utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

public class HttpRequestParam {

    public static final String DEFAULT_CHARACTER_ENCODING = "utf-8";
    private String name;
    private String value;

    public HttpRequestParam(String paramString) {
        if (StringUtils.isEmpty(paramString))
            throw new IllegalArgumentException("parameter 형식이 잘못되었습니다. paramString : " + paramString);
        String[] parts = paramString.split("=");
        if (parts.length > 2 ) throw new IllegalArgumentException("parameter 형식이 잘못되었습니다. paramString : " + paramString);

        if (parts.length == 1 ) {
            this.name = parts[0];
            this.value = "";
        }

        if ( parts.length == 2 ){
            this.name = parts[0];
            this.value = parts[1];
        }
    }

    private HttpRequestParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        try {
            return URLDecoder.decode(value, DEFAULT_CHARACTER_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new HttpRequestParamException("http request param decode 중 문제가 발생했습니다. (value: " + value + ")", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestParam that = (HttpRequestParam) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
