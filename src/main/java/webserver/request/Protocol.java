package webserver.request;

public enum Protocol {
    HTTP("HTTP/1.1");

    private final String message;

    Protocol(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
