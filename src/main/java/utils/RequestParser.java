package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParser {

    private static final int REQUEST_PATH_INDEX = 0;
    private static final int REQUEST_PARAM_INDEX = 1;
    private static final int REQUEST_PARAM_KEY_INDEX = 0;
    private static final int REQUEST_PARAM_VALUE_INDEX = 1;
    private static final int REQUEST_METHOD_INDEX = 0;
    private static final int REQUEST_CONTENT_LENGTH_INDEX = 1;
    private static final String FIRST_LINE_DELIMITER = " ";
    private static final String ENCODING = "UTF-8";
    private static final String COLON = ":";
    private static final String REQUEST_URL_DELIMITER = "\\?";
    private static final String REQUEST_PARAM_DELIMITER = "&";
    private static final String REQUEST_PARAM_KEY_VALUE_DELIMITER = "=";

    public static String getRequestPath(String line) {
        return getRequestUrl(line).split(REQUEST_URL_DELIMITER)[REQUEST_PATH_INDEX];
    }

    private static String getRequestUrl(String line) {
        return line.split(FIRST_LINE_DELIMITER)[1];
    }

    public static Map<String, String> getRequestParams(String line) {
        return getRequestParamsFromBody(getRequestUrl(line).split(REQUEST_URL_DELIMITER)[REQUEST_PARAM_INDEX]);
    }

    public static String getMethod(String line) {
        return line.split(FIRST_LINE_DELIMITER)[REQUEST_METHOD_INDEX];
    }

    public static Integer getContentLength(String line) {
        return Integer.parseInt(line.split(COLON)[REQUEST_CONTENT_LENGTH_INDEX].trim());
    }

    public static String getHost(String line) {
        return line.substring(line.indexOf(COLON) + 1).trim();
    }

    public static Map<String, String> getRequestParamsFromBody(String line) {

        return Arrays.stream(line.split(REQUEST_PARAM_DELIMITER))
                .map(attribute -> attribute.split(REQUEST_PARAM_KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(param -> param[REQUEST_PARAM_KEY_INDEX], param -> decode(param[REQUEST_PARAM_VALUE_INDEX])));
    }

    private static String decode(String s) {
        try {
            return URLDecoder.decode(s, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return s;
        }
    }
}
