package webserver;

import java.util.*;

public class HttpHeaders {

    private Map<String, List<String>> loweredKeyValueListMap = new HashMap<>();

    public void addHeader(String k, String v) {
        loweredKeyValueListMap.compute(k.toLowerCase(), (key, vals) -> {
            if (vals == null) {
                vals = new ArrayList<>();
            }

            vals.add(v);
            return vals;
        });
    }

    public void addHeaders(HttpHeaders otherHeaders) {
        for (Map.Entry<String, List<String>> entry : otherHeaders.entrySet()) {
            for (String value : entry.getValue()) {
                addHeader(entry.getKey(), value);
            }
        }
    }

    public String getFirstHeaderValue(String key) {
        List<String> vals = loweredKeyValueListMap.get(key.toLowerCase());
        if (vals == null || vals.isEmpty()) {
            return null;
        }

        return vals.get(0);
    }

    public Set<Map.Entry<String, List<String>>> entrySet() {
        return loweredKeyValueListMap.entrySet();
    }

}
