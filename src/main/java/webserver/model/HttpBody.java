package webserver.model;

import utils.StringUtils;

public class HttpBody {
    String body = "";

    public HttpBody() {

    }

    public void add(String newBody) {
        body = body.concat(newBody);
    }

    public String getBody() {
        return body;
    }

    public String toString() {
        return StringUtils.concatThree(StringUtils.NEW_LINE, body, StringUtils.NEW_LINE);
    }
}
