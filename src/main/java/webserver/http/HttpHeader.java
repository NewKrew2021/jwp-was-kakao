package webserver.http;

import utils.StringUtils;

import java.text.MessageFormat;
import java.util.Objects;

public class HttpHeader {
    private String key;
    private String value;

    public HttpHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public HttpHeader(String header) {
        if(StringUtils.isEmpty(header)) throw new IllegalArgumentException("header 형식이 올바르지 않습니다 ( header 가 null or 빈문자열 )");
        String [] parts = header.split(":");
        key = parts[0].trim();
        value = parts[1].trim();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}: {1}", key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpHeader that = (HttpHeader) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
