package utils;

public class RequestHeaderParser {

    private static final String delimiter = " ";

    public static String getRequestPath(String requestHeaderFirstLine) {
        return requestHeaderFirstLine.split(delimiter)[1];
    }
}
