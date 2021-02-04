package webserver.http;

import com.google.common.collect.Maps;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Parameters {
    private static final int SHOULD_CONTAIN_ONLY_KEY_AND_VALUE = 2;
    private static final String EMPTY_PARAMETER = "";
    private final Map<String, String> parameters = Maps.newHashMap();

    public void saveParameters(String[] parameterValues) {
        for (String parameterValue : parameterValues) {
            saveParameter(parameterValue);
        }
    }

    public void saveParameter(String parameterValue) {
        String[] parameterAndValue = parameterValue.split("=");
        if (!isValidParameter(parameterAndValue)) {
            return;
        }

        String key = parameterAndValue[0];
        String value = parameterAndValue[1];

        parameters.put(key, URLDecoder.decode(value, StandardCharsets.UTF_8));
    }

    public String get(String parameter) {
        return parameters.getOrDefault(parameter, EMPTY_PARAMETER);
    }

    private boolean isValidParameter(String[] parameterAndValue) {
        return parameterAndValue.length == SHOULD_CONTAIN_ONLY_KEY_AND_VALUE
                && !parameterAndValue[1].isEmpty();
    }
}
