package model;

public enum HttpStatus {
    HTTP_OK(200, "OK"),
    HTTP_FOUND(302, "FOUND");

    private final int code;
    private final String desc;

    HttpStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String descHttpStatusCode() {
        return String.format("%d %s", this.code, this.desc);
    }
}
