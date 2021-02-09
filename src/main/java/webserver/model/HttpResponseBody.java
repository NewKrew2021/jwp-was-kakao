package webserver.model;

import utils.StringUtils;

public class HttpResponseBody {
    private String body;

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
