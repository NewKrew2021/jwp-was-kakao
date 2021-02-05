package webserver.domain;

public enum HttpStatus {
    OK_200("HTTP/1.1 200 OK \r\n"),
    FOUND_302("HTTP/1.1 302 Found \r\n"),
    NOT_FOUND_404("HTTP/1.1 404 NOT Found \r\n"),
    BAD_REQUEST_400("HTTP/1.1 400 Bad Request \r\n"),
    INTERNAL_SERVER_ERROR_500("HTTP/1.1 500 Internal Server Error \r\n");

    private final String header;

    HttpStatus(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
