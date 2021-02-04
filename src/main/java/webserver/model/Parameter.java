package webserver.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Parameter {
    public static final String USERID = "userId";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    private Map<String, String> parameters = new HashMap<>();

    public Parameter(String parameterString) {
        Arrays.stream(
                parameterString.split("&"))
                .forEach(rawParameter -> {
                    String[] split = rawParameter.split("=");
                    this.parameters.put(split[0], split[1]);
                });
    }

    public String get(String key) {
        return parameters.get(key);
    }

}
