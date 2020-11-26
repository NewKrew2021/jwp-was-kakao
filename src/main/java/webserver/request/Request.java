package webserver.request;

public class Request {
    private final RequestHeader header;
    private final String body;

    public Request(RequestHeader header, String body) {
        this.header = header;
        this.body = body;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }
}
