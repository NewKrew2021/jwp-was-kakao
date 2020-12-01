package webserver.http;

public enum ContentType {
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded");

    public static final String CONTENT_TYPE = "Content-Type";
    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isEqualTo(String rawContentType) {
        return value.equals(rawContentType);
    }
}
