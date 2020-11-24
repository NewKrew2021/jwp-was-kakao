package webserver.http;

import java.util.Objects;

public class HttpRequestParam {
    public static HttpRequestParam empty = new HttpRequestParam("", "");
    private String name;
    private String value;

    public HttpRequestParam(String paramString){
        String[] parts = paramString.split("=");
        if( parts.length == 0 ) throw new IllegalArgumentException("parameter 형식이 잘못되었습니다. paramString : " + paramString);

        name = parts[0];
        if( parts.length == 2 )
            value = parts[1];
    }

    public HttpRequestParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
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
