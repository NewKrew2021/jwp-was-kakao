package webserver.http;

import java.io.UnsupportedEncodingException;

public class RequestBody {

    private final String body;
    private final QueryString queryString;

    private RequestBody(String body) throws UnsupportedEncodingException {
        this.body = body;
        this.queryString = new QueryString(body);
    }

    public static RequestBody from(String body) throws UnsupportedEncodingException {
        return new RequestBody(body);
    }

    public String getBody() {
        return body;
    }

    public String getParameter(String key) {
        return queryString.getParameter(key);
    }

    public QueryString getQueryString() {
        return queryString;
    }
}
