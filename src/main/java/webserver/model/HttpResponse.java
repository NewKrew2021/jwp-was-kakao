package webserver.model;

import utils.StringUtils;

public class HttpResponse {
    public static final String HTTP_VERSION = "HTTP/1.1";

    private HttpStatus status;
    private HttpHeader header;
    private HttpBody body;

    public HttpResponse() {
        status = HttpStatus.NOT_FOUND;
        header = new HttpHeader();
        body = new HttpBody();
    }

    public void addHeader(String key, String value) {
        header.add(key, value);
    }

    public void addCookie(String key, String value) {
        header.addCookie(key, value);
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setBody(String body) {
        this.body.setBody(body);
    }

    public String toString() {
        String startString = String.join(
                StringUtils.WHITE_SPACE,
                HTTP_VERSION,
                String.valueOf(status.getCode()),
                status.getMessage()
        );

        String headerString = header.toString();
        String bodyString = body.toString();


        return startString + StringUtils.NEW_LINE + headerString + StringUtils.NEW_LINE + bodyString;
    }
}
