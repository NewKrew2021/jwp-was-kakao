package webserver;

import dto.RequestValue;

public class RequestBody {

    private final String body;

    private RequestBody(String body) {
        this.body = body;
    }

    public static RequestBody of(RequestValue requestValue) {
        return new RequestBody(requestValue.getBody());
    }

    public String getBody() {
        return body;
    }
}
