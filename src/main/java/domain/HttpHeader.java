package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {
    private Map<String, String> headers = new HashMap<>();
    private Cookie cookie;

    public HttpHeader() {

    }

    public HttpHeader(List<String> headers) {
        setHeader(headers);
        setCookie();
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    private void setHeader(List<String> headerStrings) {
        for(String headerString : headerStrings) {
            String[] splitedHeader = headerString.split(": ");
            this.headers.put(splitedHeader[0], splitedHeader[1]);
        }
    }

    private void setCookie() {
        if(headers.containsKey("Cookie")) {
            cookie = new Cookie(headers.get("Cookie"));
            headers.remove("Cookie");
        }
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getCookie(String key) {
        return cookie.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            sb.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }
        if(cookie != null && !cookie.isEmpty()) {
            sb.append(cookie);
        }
        sb.append("\r\n");
        return sb.toString();
    }
}
