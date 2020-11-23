package webserver.http;

import java.text.MessageFormat;

public class HttpHeader {
    private String key;
    private String value;

    public HttpHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}: {1}", key, value);
    }
}
