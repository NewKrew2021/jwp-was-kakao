package annotation.web;

public enum ResponseStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(400, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CONFLICT(409, "Conflict"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int statusCode;
    private final String message;

    ResponseStatus(int statusCode, String message) {
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
