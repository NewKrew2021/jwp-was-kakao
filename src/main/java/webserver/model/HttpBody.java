package webserver.model;

import utils.StringUtils;

public class HttpBody {
    String body;

    public HttpBody() {
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String toString() {
        return StringUtils.concatThree(StringUtils.NEW_LINE, body, StringUtils.NEW_LINE);
    }
}
