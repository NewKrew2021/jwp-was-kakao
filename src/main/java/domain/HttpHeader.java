package domain;

import exception.HeaderNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {
    private static final String HEADER_KEY_VALUE_DELIMITER = ": ";
    public static final String HEADER_COOKIE = "Cookie";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_CONTENT_LOCATION = "Content-Location";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_LOCATION = "Location";
    public static final String HEADER_SET_COOKIE = "Set-Cookie";

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
            String[] splitedHeader = headerString.split(HEADER_KEY_VALUE_DELIMITER);
            this.headers.put(splitedHeader[0], splitedHeader[1]);
        }
    }

    private void setCookie() {
        if(headers.containsKey(HEADER_COOKIE)) {
            cookie = new Cookie(headers.get(HEADER_COOKIE));
            headers.remove(HEADER_COOKIE);
        }
    }

    public String getHeader(String key) throws HeaderNotFoundException {
        if(!headers.containsKey(key)) {
            throw new HeaderNotFoundException();
        }
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
