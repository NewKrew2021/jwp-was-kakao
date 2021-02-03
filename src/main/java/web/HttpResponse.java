package web;

import org.springframework.http.HttpStatus;
import utils.IOUtils;

public class HttpResponse {
    private static final String HTTP_1_1 = "HTTP/1.1 ";

    private final HttpHeaders httpHeaders;
    private final HttpStatus httpStatus;
    private HttpBody httpBody;

    private HttpResponse(HttpStatus httpStatus, HttpHeaders httpHeaders, HttpBody httpBody) {
        this.httpStatus = httpStatus;
        this.httpHeaders = httpHeaders;
        this.httpBody = httpBody;
    }

    public static HttpResponse of(HttpStatus httpStatus) {
        return new HttpResponse(httpStatus, HttpHeaders.empty(), HttpBody.empty());
    }

    public void addHeader(String key, String value) {
        httpHeaders.add(key, value);
    }

    public void setBody(String body) {
        httpBody = new HttpBody(body);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HTTP_1_1).append(httpStatus.value()).append(" ").append(httpStatus.getReasonPhrase()).append(IOUtils.CRLF);
        if (!httpHeaders.isEmpty()) {
            sb.append(httpHeaders);
        }
        if (!httpBody.equals(HttpBody.empty())) {
            sb.append(IOUtils.CRLF).append(httpBody.getBody()).append(IOUtils.CRLF);
        }

        return sb.toString();
    }
}
