package domain;

public enum HttpVersion {
    HTTP1_1("1.1"), HTTP2("2");

    private final String value;

    HttpVersion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString(){
        return "HTTP/" + value;
    }
}
