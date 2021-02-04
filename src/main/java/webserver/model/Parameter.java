package webserver.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Parameter {

    private Map<String, String> parameters;

    public Parameter(String parameterString) {
        this.parameters = new HashMap<>();
        Arrays.stream(parameterString.split("&"))
                .forEach(rawParameter -> {
                    String[] split = rawParameter.split("=");
                    this.parameters.put(split[0], split[1]);
                });
    }

    public String get(String key) {
        return parameters.get(key);
    }

}
