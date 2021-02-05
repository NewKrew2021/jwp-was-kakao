package model;

import java.util.Map;

public class Parameter {
    private final Map<String, String> parameter;

    public Parameter(Map<String, String> parameter) {
        this.parameter = parameter;
    }

    public String get(String key) {
        return parameter.get(key);
    }

}
