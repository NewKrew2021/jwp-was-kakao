package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpParameters {
    private final Map<String, String> parameters;

    public HttpParameters() {
        this.parameters = new HashMap<>();
    }

    public void addParameter(String name, String value) {
        parameters.put(name, value);
    }

    public String getValue(String name) {
        return parameters.get(name);
    }
}
