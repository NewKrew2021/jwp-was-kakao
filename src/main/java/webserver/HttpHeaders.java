package webserver;

import java.util.*;

public class HttpHeaders {

    private Map<String, List<String>> loweredHeaderKeyValueListMap = new HashMap<>();

    public void addHeader(String k, String v) {
        loweredHeaderKeyValueListMap.compute(k.toLowerCase(), (key, vals) -> {
            if (vals == null) {
                vals = new ArrayList<>();
            }

            vals.add(v);
            return vals;
        });
    }

    public void addHeaders(HttpHeaders setCookieHeaders) {
        loweredHeaderKeyValueListMap.putAll(setCookieHeaders.loweredHeaderKeyValueListMap);
    }

    public String getFirstHeaderValue(String key) {
        List<String> vals = loweredHeaderKeyValueListMap.get(key.toLowerCase());
        if (vals == null || vals.isEmpty()) {
            return null;
        }

        return vals.get(0);
    }

    public Set<Map.Entry<String, List<String>>> entrySet() {
        return loweredHeaderKeyValueListMap.entrySet();
    }

}
