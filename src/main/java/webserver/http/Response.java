package webserver.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Response {
    private final List<String> headers = new ArrayList<>();
    private Object model;
    private String viewName;
    private byte[] body;
    private ResponseStatus status = ResponseStatus.OK;

    public void setHeaders(String headers) {
        this.headers.add(headers);
    }

    public List<String> getHeaders() {
        ArrayList<String> result = new ArrayList<>();
        result.add(String.format("HTTP/1.1 %s ", status.getStatusValue()));
        if (body != null) {
            result.add("Content-Length: " + body.length);
        }
        result.addAll(headers);
        return Collections.unmodifiableList(result);
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public boolean isRedirect() {
        return status == ResponseStatus.FOUND || headers.stream()
                .anyMatch(header -> header.startsWith("Location: "));
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
