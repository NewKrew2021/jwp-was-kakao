package annotation.web;

public enum ResponseMethod {
    OK(200, "OK"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request");

    private final int statusCode;
    private final String message;

    ResponseMethod(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
