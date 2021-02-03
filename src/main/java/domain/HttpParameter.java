package domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpParameter {
    private static final String PARAMETER_DELIMITER = "&";
    private static final String PARAMETER_KEY_VALUE_DELIMITER = "=";

    private Map<String, String> parameters;

    public HttpParameter(String parameterString) {
        this.parameters = new HashMap<>();
        Arrays.stream(parameterString.split(PARAMETER_DELIMITER))
                .forEach(rawParameter -> {
                    String[] split = rawParameter.split(PARAMETER_KEY_VALUE_DELIMITER);
                    this.parameters.put(split[0], split[1]);
                });
    }

    public String get(String key) {
        return parameters.get(key);
    }

}
