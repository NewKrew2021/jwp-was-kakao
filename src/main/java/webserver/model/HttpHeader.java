package webserver.model;

import utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {
    public static final String HEADER_DELIMITER = ": ";
    public static final String HEADER_END_WITH = "";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_LOCATION = "Content-Location";
    public static final String LOCATION = "Location";
    public static final String COOKIE = "Cookie";

    private Map<String, String> headers = new HashMap<>();
    private Cookie cookie = new Cookie();

    public HttpHeader() {

    }

    public HttpHeader(List<String> headers) {
        setHeader(headers);
        setCookie();
    }

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public void addCookie(String key, String value) {
        cookie.add(key, value);
    }

    private void setHeader(List<String> headerStrings) {
        for (String headerString : headerStrings) {
            headers.put(headerString.split(HEADER_DELIMITER)[0], headerString.split(HEADER_DELIMITER)[1]);
        }
    }

    private void setCookie() {
        if (headers.containsKey(COOKIE)) {
            cookie = new Cookie(headers.get(COOKIE));
            headers.remove(COOKIE);
        }
    }

    public String getHeader(String key) {
        return headers.get(key);
    }


    public String getCookie(String key) {
        return cookie.get(key);
    }

    public String toString(String key) {
        if (!headers.containsKey(key)) {
            return null;
        }

        String headerString = String.join(HEADER_DELIMITER, key, headers.get(key));
        return headerString.concat(StringUtils.NEW_LINE);
    }

    public String toString() {
        String str = "";

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String headerString = String.join(HEADER_DELIMITER, entry.getKey(), entry.getValue());
            str = StringUtils.concatThree(str, headerString, StringUtils.NEW_LINE);
        }
        str = StringUtils.concatThree(str, cookie.toString(), StringUtils.NEW_LINE);
        return str;
    }
}
