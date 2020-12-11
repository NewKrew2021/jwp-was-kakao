package webserver.http;

import java.text.MessageFormat;

public enum HttpStatus {
    x200_OK(200, "OK"),
    x201_Created(201, "Created"),
    x302_Found(302, "Found"),
    x400_BadRequest(400, "Bad Request"),
    x401_Unauthorized(401, "Unauthorized"),
    x404_NotFound(404, "Not Found"),
    x500_InternalServerError(500, "Internal Server Error");

    private final String reasonPhrase;
    private final int statusCode;

    HttpStatus(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} {1}", statusCode, reasonPhrase);
    }
}
