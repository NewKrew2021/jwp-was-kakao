package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParamMap {

    private Map<String, String> kvMap;

    public ParamMap() {
        kvMap = new HashMap<>();
    }

    public ParamMap(String s, String listDelim, String kvDelim, Function<String, String> applyToValue) {
        kvMap = Arrays.stream(s.split(listDelim))
                .map(param -> param.split(kvDelim, 2))
                .filter(kv -> kv[0].length() > 0)
                .collect(Collectors.toMap(kv -> kv[0], kv -> applyToValue.apply(kv[1])));
    }

    public ParamMap put(String k, String v) {
        kvMap.put(k, v);
        return this;
    }

    public String get(String k) {
        return kvMap.get(k);
    }

    public String getOrDefault(String k, String defaultValue) {
        return kvMap.getOrDefault(k, defaultValue);
    }

    public String join(String listDelim, String kvDelim) {
        return kvMap.entrySet().stream()
                .map(entry -> entry.getKey() + kvDelim + entry.getValue())
                .collect(Collectors.joining(listDelim));
    }

}
