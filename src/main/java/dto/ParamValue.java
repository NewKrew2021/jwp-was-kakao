package dto;

import utils.DecodeUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParamValue {

    private static final String REGEX_AMPERSAND = "&";
    private static final String REGEX_EQUALS = "=";

    private final Map<String, String> paramMap;

    private ParamValue(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public static ParamValue of(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return new ParamValue(map);
    }

    public static Optional<ParamValue> of(Optional<String> optionalParam) {
        Map<String, String> paramMap = new HashMap<>();

        if (optionalParam.isPresent()) {
            String[] splitParam = optionalParam.get().split(REGEX_AMPERSAND);

            Arrays.stream(splitParam).forEach(v -> {
                String[] data = v.split(REGEX_EQUALS);
                paramMap.put(data[0], DecodeUtils.decodeUTF8(data[1]));
            });

            return Optional.of(new ParamValue(paramMap));
        }

        return Optional.empty();
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public String getValue(String key) {
        return paramMap.get(key);
    }
}
