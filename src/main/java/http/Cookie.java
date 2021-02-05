package http;

public class Cookie {
    private final String key;
    private final String value;
    private final String path;

    private Cookie(String key, String value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public static Cookie of(String key, String value, String path) {
        return new Cookie(key, value, path);
    }

    public static Cookie of() {
        return new Cookie(null, null, null);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return key + "=" + value + "; Path=" + path;
    }
}
