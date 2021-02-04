package webserver;

import utils.ParseUtils;

import java.util.Map;

public class Cookie {

    private String key;
    private String value;

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Cookie(String text) {
        Map.Entry<String, String> entry = ParseUtils.parseParametersByEqual(text);
        this.key = entry.getKey();
        this.value = entry.getValue();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + "=" + value + "; path=/";
    }
}
