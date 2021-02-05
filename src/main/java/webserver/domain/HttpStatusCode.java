package webserver.domain;

public enum HttpStatusCode {

    OK(200, "OK"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CONFLICT(409, "Conflict"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int statusCode;

    private final String reasonPhrase;


    HttpStatusCode(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }


    public int value() {
        return this.statusCode;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public String getPhrase() {
        return this.statusCode + " " + this.reasonPhrase;
    }
}
