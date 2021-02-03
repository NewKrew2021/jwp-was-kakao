package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {
    private Map<String, String> headers = new HashMap<>();
    private Cookie cookie;

    public HttpHeader(List<String> headers) {
        setHeader(headers);
        setCookie();
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

}
