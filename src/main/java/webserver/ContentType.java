package webserver;

public enum ContentType {
    HTML("text/html;charset=utf-8"),
    CSS("text/css");

    private final String message;

    ContentType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
