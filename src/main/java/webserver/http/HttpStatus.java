package webserver.http;

public enum HttpStatus {
    x201_Created(201, "Created");

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
}
