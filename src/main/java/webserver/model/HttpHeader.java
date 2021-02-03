package webserver.model;

import org.checkerframework.checker.units.qual.C;
import utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {
    private static final String HEADER_DELIMITER = ": ";
    private static final String NEW_LINE = "\r\n";
    
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
        for(String headerString : headerStrings) {
            headers.put(headerString.split(HEADER_DELIMITER)[0], headerString.split(HEADER_DELIMITER)[1]);
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

    public List<String> getHeaders() {
        return (List<String>) headers.values();
    }

    public String getCookie(String key) {
        return cookie.get(key);
    }

    public String toString(String key) {
        if (!headers.containsKey(key)) {
            return null;
        }

        String headerString = String.join(HEADER_DELIMITER, key, headers.get(key));
        return headerString.concat(NEW_LINE);
    }

    public String toStringCookie(String key) {
        return cookie.toString(key);
    }

    public String toString() {
        String str = "";

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String headerString = String.join(HEADER_DELIMITER, entry.getKey(), entry.getValue());
            str = StringUtils.concatThree(str, headerString, NEW_LINE);
        }
        str = StringUtils.concatThree(str, cookie.toString(), NEW_LINE);
        return str;
    }
}
