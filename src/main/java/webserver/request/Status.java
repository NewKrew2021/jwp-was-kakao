package webserver.request;

public enum Status {
    OK("200 OK"),
    REDIRECT("302 FOUND");

    String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
