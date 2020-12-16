package webserver;

import utils.Utils;
import webserver.constant.HttpHeader;
import webserver.constant.HttpMessage;
import webserver.constant.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final int statusCode;
    private final String reasonPhrase;
    private final Map<String, String> loweredKeyHeaders;
    private final byte[] body;

    public static class Builder {

        private int statusCode;
        private String reasonPhrase;
        private byte[] body;
        private Map<String, String> loweredKeyHeaders = new HashMap<>();

        public static Builder prepare() {
            return new Builder();
        }

        public Builder status(int statusCode, String reasonPhrase) {
            this.statusCode = statusCode;
            this.reasonPhrase = reasonPhrase;
            return this;
        }

        public Builder status(HttpStatus httpStatus) {
            this.statusCode = httpStatus.getStatusCode();
            this.reasonPhrase = httpStatus.getReasonPhrase();
            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public Builder header(String k, String v) {
            loweredKeyHeaders.put(k.toLowerCase(), v);
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(statusCode, reasonPhrase, loweredKeyHeaders, body);
        }

    }

    public HttpResponse(int statusCode, String reasonPhrase, Map<String, String> loweredKeyHeaders, byte[] body) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.loweredKeyHeaders = loweredKeyHeaders;
        this.body = Utils.defaultIfNull(body);

        // add c-len if not exists
        if (getHeaderValue(HttpHeader.CONTENT_LENGTH) == null) {
            addHeader(HttpHeader.CONTENT_LENGTH, Long.toString(this.body.length));
        }
    }

    private void addHeader(String k, String v) {
        loweredKeyHeaders.put(k, v);
    }

    public String getHeaderValue(String key) {
        return loweredKeyHeaders.get(key.toLowerCase());
    }

    @Override
    public String toString() {
        String s = String.format("%s %d %s" + HttpMessage.CRLF, HttpMessage.DEFAULT_HTTP_VERSION, statusCode, reasonPhrase);

        for (Map.Entry<String, String> headerEntry : loweredKeyHeaders.entrySet()) {
            s += String.format("%s: %s" + HttpMessage.CRLF, headerEntry.getKey(), headerEntry.getValue());
        }

        s += HttpMessage.CRLF;

        if (body.length > 0) {
            s += new String(body);
        }

        return s;
    }

}
