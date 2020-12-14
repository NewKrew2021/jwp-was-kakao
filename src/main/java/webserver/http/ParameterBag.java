package webserver.http;

import java.util.Map;

public class ParameterBag {

    private final Map<String, String> parameters;

    public ParameterBag(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}
