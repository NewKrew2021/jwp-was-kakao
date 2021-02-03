package http;

public enum HttpStatus {
    OK("OK", 200),
    FOUND("Found", 302),
    NOT_FOUND("Not Found", 404);

    public String header;
    public int number;

    HttpStatus(String header, int number) {
        this.header = header;
        this.number = number;
    }

    @Override
    public String toString() {
        return number + " " + header;
    }
}
