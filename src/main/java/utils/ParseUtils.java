package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ParseUtils {

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static Map<String, String> parseParametersByAmpersand(String text) throws UnsupportedEncodingException {
        Map<String, String> results = new HashMap<>();
        String[] tokens = text.split("&");
        for (String token : tokens) {
            String[] values = token.split("=");
            results.put(values[KEY_INDEX].trim(), URLDecoder.decode(values[VALUE_INDEX],
                    java.nio.charset.StandardCharsets.UTF_8.toString()).trim());
        }
        return results;
    }

    public static Map<String, String> parseParametersByColon(String line) {
        Map<String, String> results = new HashMap<>();
        String[] splitLine = line.split(":");
        results.put(splitLine[KEY_INDEX].trim(), splitLine[VALUE_INDEX].trim());
        return results;
    }

    public static String parseExtension(String content) {
        String[] arr = content.split("\\.");
        return arr[arr.length - 1];
    }
}
