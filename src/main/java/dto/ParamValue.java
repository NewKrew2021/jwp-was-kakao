package dto;

import utils.DecodeUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ParamValue {

    private static final String REGEX_AMPERSAND = "&";
    private static final String REGEX_EQUALS = "=";

    private final Map<String, String> paramMap;

    public ParamValue(Map<String, String> paramMap) {
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

    public List<String> parseParam(Class<? extends Object> objectClass) {
        Field[] fields = objectClass.getDeclaredFields();

        return Arrays.stream(fields)
                .map(Field::getName)
                .map(this::getValue)
                .collect(Collectors.toList());
    }

    public String getValue(String key) {
        return paramMap.get(key);
    }
}
