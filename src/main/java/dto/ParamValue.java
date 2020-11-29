package dto;

import utils.MessageUtils;

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

    public static ParamValue of(Optional<String> optionalParam) {
        Map<String, String> paramMap = new HashMap<>();

        String param = validate(optionalParam);
        String[] splitParam = param.split(REGEX_AMPERSAND);

        Arrays.stream(splitParam).forEach(v -> {
            String[] data = v.split(REGEX_EQUALS);
            paramMap.put(data[0], data[1]);
        });

        return new ParamValue(paramMap);
    }

    public String getValue(String key) {
        return paramMap.get(key);
    }

    private static String validate(Optional<String> param) {
        if (param.isPresent()) {
            return param.get();
        }
        throw new IllegalArgumentException(MessageUtils.INVALID_PARAM_VALUE);
    }

}
