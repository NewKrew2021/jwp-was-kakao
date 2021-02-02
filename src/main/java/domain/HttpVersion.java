package domain;

public enum HttpVersion {
    HTTP1_1("1.1"), HTTP2("2");

    private final String value;

    HttpVersion(String value) {
        this.value = value;
    }

    HttpVersion ofDefault(){
        return HTTP1_1;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString(){
        return "HTTP/" + value;
    }
}
