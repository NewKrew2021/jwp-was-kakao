package webserver;

import java.util.Objects;

public class Cookie {

    private final String key;
    private final String value;
    private final String path;

    public Cookie(String key, String value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public static Cookie fromRequest(String line) {
        String[] tokens = line.split("=");
        return new Cookie(tokens[0].trim(), tokens[1].trim(), null);
    }

    public String getContent() {
        return String.format("%s=%s", key, value);
    }

    public String getKey() {
        return key;
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cookie cookie = (Cookie) o;

        if (!Objects.equals(key, cookie.key)) return false;
        if (!Objects.equals(value, cookie.value)) return false;
        return Objects.equals(path, cookie.path);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
