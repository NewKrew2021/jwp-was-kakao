package utils;


import exception.InvalidPatternException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtils {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static Matcher getMatcher(String patternString, String requestLine) throws InvalidPatternException {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(requestLine);

        if (!matcher.find()) {
            throw new InvalidPatternException();
        }
        return matcher;
    }

    public static Map<String, String> parseRequestParameters(String text) throws UnsupportedEncodingException {
        Map<String, String> results = new HashMap<>();
        String[] tokens = text.split("&");
        for (String token : tokens) {
            String[] values = token.split("=");
            results.put(values[KEY_INDEX].trim(), URLDecoder.decode(values[VALUE_INDEX],
                    java.nio.charset.StandardCharsets.UTF_8.toString()).trim());
        }
        return results;
    }

    public static Map.Entry<String, String> parseParametersByColon(String line) {
        String[] splitLine = line.split(":");
        return new AbstractMap
                .SimpleEntry<String, String>(splitLine[KEY_INDEX].trim(), splitLine[VALUE_INDEX].trim());
    }

    public static String parseExtension(String content) {
        String[] arr = content.split("\\.");
        return arr[arr.length - 1];
    }

    public static Map<String, String> parseCookie(String cookie) {
        Map<String, String> results = new HashMap<>();
        String[] tokens = cookie.split(";");
        for (String token : tokens) {
            String[] values = token.split("=");
            results.put(values[KEY_INDEX].trim(), values[VALUE_INDEX].trim());
        }
        return results;
    }
}
