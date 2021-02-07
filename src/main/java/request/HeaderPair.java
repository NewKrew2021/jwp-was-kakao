package request;

import java.util.Objects;

public class HeaderPair {
    private String key;
    private String value;

    private HeaderPair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static HeaderPair of(String key, String value){
        return new HeaderPair(key, value);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderPair that = (HeaderPair) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
