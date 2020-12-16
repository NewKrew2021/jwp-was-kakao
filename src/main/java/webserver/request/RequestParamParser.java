package webserver.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParamParser {

    private static final String ENCODING = "UTF-8";

    public static Map<String, String> parseRequestParams(String line) {
        return Arrays.stream(line.split("&"))
                .map(attribute -> attribute.split("="))
                .collect(Collectors.toMap(RequestParamParser::getKey, RequestParamParser::getValue));
    }

    private static String getKey(String[] tokens) {
        assert tokens.length >= 1;
        return tokens[0];
    }

    private static String getValue(String[] tokens) {
        if (tokens.length == 2) {
            return decode(tokens[1]);
        }
        return "";
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
