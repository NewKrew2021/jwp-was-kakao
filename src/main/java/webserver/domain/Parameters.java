package webserver.domain;

import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Stream;

public class Parameters {
    private static final String AMPERSAND = "&";
    private static final String EQUAL_SIGN = "=";
    private Map<String, String> parameters = Maps.newHashMap();

    public Parameters(String queryString, String bodyData) {
        makeParameters(queryString);
        makeParameters(bodyData);
    }

    private void makeParameters(String line) {
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }

        Stream.of(line.split(AMPERSAND))
                .forEach(pairValue -> {
                    String[] pair = pairValue.split(EQUAL_SIGN);
                    try {
                        parameters.put(pair[0], URLDecoder.decode(pair[1], StandardCharsets.UTF_8.toString()));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
    }

    public String get(String key, String defaultValue) {
        return parameters.getOrDefault(key, defaultValue);
    }
}
