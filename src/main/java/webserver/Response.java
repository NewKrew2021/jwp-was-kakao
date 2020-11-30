package webserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Response {
    private final List<String> headers = new ArrayList<>();
    private Object model;
    private String viewName;
    private byte[] body;

    public void setHeaders(String headers) {
        this.headers.add(headers);
    }

    public List<String> getHeaders() {
        return Collections.unmodifiableList(headers);
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
        return headers.stream()
                .anyMatch(header -> header.startsWith("Location: "));
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }
}
