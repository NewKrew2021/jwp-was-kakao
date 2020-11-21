package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestBodyParser {
    private static final int REQUEST_PARAM_KEY_INDEX = 0;
    private static final int REQUEST_PARAM_VALUE_INDEX = 1;

    public static Map<String, String> getRequestParams(String line) {
        return Arrays.stream(line.split("&"))
                .map(attribute -> attribute.split("="))
                .collect(Collectors.toMap(param -> param[REQUEST_PARAM_KEY_INDEX], param -> param[REQUEST_PARAM_VALUE_INDEX]));
    }

}
