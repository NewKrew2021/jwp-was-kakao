package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestBodyParser {
    private static final int REQUEST_PARAM_KEY_INDEX = 0;
    private static final int REQUEST_PARAM_VALUE_INDEX = 1;

    public static Map<String, String> getRequestParams(String line) {
        return Arrays.stream(line.split("&"))
                .map(attribute -> attribute.split("="))
                .collect(Collectors.toMap(param -> param[REQUEST_PARAM_KEY_INDEX], param -> {
                    try {
                        return URLDecoder.decode(param[REQUEST_PARAM_VALUE_INDEX], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return param[REQUEST_PARAM_VALUE_INDEX];
                    }
                }));
    }

}
