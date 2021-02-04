package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Parameter {
    private Map<String, String> parameter;

    public Parameter(Map<String, String> parameter) {
        this.parameter = parameter;
    }

    public Parameter() {
        this.parameter = new HashMap<>();
    }

    public void merge(Parameter parameter) {
        this.parameter.putAll(parameter.parameter);
    }

    public String get(String key) {
        return parameter.get(key);
    }

    public Map<String, String> getAllParameter() {
        return Collections.unmodifiableMap(parameter);
    }
}
