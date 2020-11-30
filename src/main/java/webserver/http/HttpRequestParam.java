package webserver.http;

import utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

public class HttpRequestParam {

    public static HttpRequestParam empty = new HttpRequestParam("", "");

    private String name;
    private String value = "";

    public HttpRequestParam(String paramString) {
        if (StringUtils.isEmpty(paramString))
            throw new IllegalArgumentException("parameter 형식이 잘못되었습니다. paramString : " + paramString);

        String[] parts = paramString.split("=");
        name = parts[0];
        if (parts.length == 2)
            value = parts[1];
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
            return URLDecoder.decode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new HttpRequestParamValueEncodingException("url decode 중 문제가 발생했습니다", e);
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
