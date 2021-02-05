package webserver.domain;

import java.util.HashMap;
import java.util.Map;

public class HttpParameters {
    private final Map<String, String> parameters = new HashMap<>();

    public void add(String key, String value) {
        parameters.put(key, value);
    }

    public String get(String key) {
        return parameters.get(key);
    }

    public boolean contain(String key) {
        return parameters.containsKey(key);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void parseAndSet(String parameterString) {
        String[] parameterToken = parameterString.split("&");
        for (String token : parameterToken) {
            if (token.indexOf('=') == -1) {
                add(token, "");
                continue;
            }
            String[] keyValue = token.split("=");
            if(keyValue.length == 0){
                continue;
            }
            if(keyValue.length == 1) {
                add(keyValue[0], "");
                continue;
            }
            add(keyValue[0], keyValue[1]);
        }
    }
}
