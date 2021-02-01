package web;

public class HttpBody {
    private static final HttpBody EMPTY_BODY = new HttpBody("");
    private final String body;

    public HttpBody(String body) {
        this.body = body;
    }

    public static HttpBody empty() {
        return EMPTY_BODY;
    }

    public String getBody() {
        return body;
    }
}
