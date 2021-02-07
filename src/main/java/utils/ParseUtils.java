package utils;

import request.HeaderPair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParseUtils {

    private static final String HEADER_REGEX = ":";
    private static final int START_INDEX = 0;
    private static final String REQUEST_URL_REGEX = "\\?";
    private static final String PARAMETER_PAIR_REGEX = "&";
    private static final String PARAMETER_KEY_VALUE_REGEX = "=";
    private static final String EXTENSION_REGEX = ".";
    private static final String SESSION_REGEX = ";";

    public static HeaderPair parseHeaderPair(String header) {
        return HeaderPair.of(header.substring(START_INDEX, header.indexOf(HEADER_REGEX))
                , header.substring(header.indexOf(HEADER_REGEX) + 1).trim());
    }

    public static Map<String, String> getParameters(String parameterPairs) {
        Map<String, String> parameters = new HashMap<>();

        Arrays.stream(parameterPairs.split(PARAMETER_PAIR_REGEX))
                .map(pair -> pair.split(PARAMETER_KEY_VALUE_REGEX))
                .forEach(keyValue -> parameters.put(keyValue[0], keyValue[1]));

        return parameters;
    }

    public static String getUrlPath(String url) {
        return url.split(REQUEST_URL_REGEX)[0];
    }

    public static String getParameterPairs(String url) {
        return url.split(REQUEST_URL_REGEX)[1];
    }

    public static boolean containRequestUrlRegex(String url){
        return url.contains(REQUEST_URL_REGEX);
    }

    public static String getExtension(String path) {
        int index = path.lastIndexOf(EXTENSION_REGEX);

        if(index == -1) {
            return "";
        }

        return path.substring(index);
    }

    public static String getSessionId(String cookie){
        return cookie.split(SESSION_REGEX)[START_INDEX];
    }
}
