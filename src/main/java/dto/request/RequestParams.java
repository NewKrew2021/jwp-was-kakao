package dto.request;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {

    public static final int KEY = 0;
    public static final int VALUE = 1;

    private final Map<String, String> params = new HashMap<>();

    public RequestParams() {
    }

    public void putBy(String query) {
        String[] queries = query.split("&");
        for (String q : queries) {
            String[] keyAndValue = q.split("=");
            params.put(keyAndValue[KEY], keyAndValue[VALUE]);
        }
    }

    public String get(String key) {
        return params.get(key);
    }

    public boolean containsKey(String key) {
        return params.containsKey(key);
    }
}
