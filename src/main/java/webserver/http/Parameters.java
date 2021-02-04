package webserver.http;

import com.google.common.collect.Maps;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Parameters {
    private static final String EMPTY_PARAMETER = "";
    private final Map<String, String> parameters = Maps.newHashMap();

    public void saveParameters(String[] parameterValues) {
        for (String parameterValue : parameterValues) {
            saveParameter(parameterValue);
        }
    }

    public void saveParameter(String parameterValue) {
        if (parameterValue.isEmpty()) {
            return;
        }

        String[] parameterAndValue = parameterValue.split("=");
        String key = parameterAndValue[0];
        String value = parameterAndValue[1];

        parameters.put(key, URLDecoder.decode(value, StandardCharsets.UTF_8));
    }

    public String get(String parameter) {
        return parameters.getOrDefault(parameter, EMPTY_PARAMETER);
    }
}
