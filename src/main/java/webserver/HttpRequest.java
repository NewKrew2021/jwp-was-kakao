package webserver;

import model.User;

class HttpRequest {
    private final String method;
    private final String requestURI;
    private final String protocol;

    public HttpRequest(String method, String requestURI, String protocol) {
        this.method = method;
        this.requestURI = requestURI;
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getProtocol() {
        return protocol;
    }

    public User getUser() {
        return new User("javajigi", "password", "박재성", "javagigi@slipp.net");
    }
}
