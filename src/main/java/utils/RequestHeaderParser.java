package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestHeaderParser {

    private static final String FIRST_LINE_DELIMITER = " ";
    private static final int REQUEST_PATH_INDEX = 0;
    private static final int REQUEST_PARAM_INDEX = 1;
    private static final int REQUEST_PARAM_KEY_INDEX = 0;
    private static final int REQUEST_PARAM_VALUE_INDEX = 1;


    public static String getRequestPath(String line) {
        return filterHttpMethodAndVersion(line).split("\\?")[REQUEST_PATH_INDEX];
    }

    private static String filterHttpMethodAndVersion(String line) {
        return line.split(FIRST_LINE_DELIMITER)[1];
    }

    public static Map<String, String> getRequestParams(String line) {
        return Arrays.stream(filterHttpMethodAndVersion(line).split("\\?")[REQUEST_PARAM_INDEX].split("&"))
                .map(attribute -> attribute.split("="))
                .collect(Collectors.toMap(param -> param[REQUEST_PARAM_KEY_INDEX], param -> param[REQUEST_PARAM_VALUE_INDEX]));
    }

    public static String getMethod(String line) {
        return line.split(FIRST_LINE_DELIMITER)[0];
    }

    public static Integer getContentLength(String line) {
        return Integer.parseInt(line.split(":")[1].trim());
    }

    public static String getHost(String line) {
        return line.substring(line.indexOf(":") + 1).trim();
    }
}
