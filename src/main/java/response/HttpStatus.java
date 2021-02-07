package response;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found");

    private final int httpCode;
    private final String message;

    HttpStatus(int httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpCode() {
        return httpCode;
    }

    @Override
    public String toString() {
        return httpCode + " " + message;
    }

}
