package utils;

public class ParseUtils {

    private static final String HEADER_REGEX = ":";
    private static final int START_INDEX = 0;

    public static String parseHeaderValue(String header) {
        return header.substring(header.indexOf(HEADER_REGEX) + 1).trim();
    }

    public static String parseHeaderKey(String header) {
        return header.substring(START_INDEX, header.indexOf(HEADER_REGEX));
    }
}
