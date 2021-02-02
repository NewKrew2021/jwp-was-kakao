package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParameterUtils {

    private static final String REQUEST_URL_REGEX = "\\?";
    private static final String PARAMETER_PAIR_REGEX = "&";
    private static final String PARAMETER_KEY_VALUE_REGEX = "=";

    public static Map<String, String> getParameters(String requestUrl) {
        Map<String, String> parameters = new HashMap<>();

        Arrays.stream(requestUrl.split(REQUEST_URL_REGEX)[1].split(PARAMETER_PAIR_REGEX))
                .map(pair -> pair.split(PARAMETER_KEY_VALUE_REGEX))
                .forEach(keyValue -> parameters.put(keyValue[0], keyValue[1]));

        return parameters;
    }
}
