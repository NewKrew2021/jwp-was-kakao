package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.AbstractMap;
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

    public static Map.Entry<String, String> parseParametersByColon(String line) {
        String[] splitLine = line.split(":");
        return new AbstractMap
                .SimpleEntry<>(splitLine[KEY_INDEX].trim(), splitLine[VALUE_INDEX].trim());
    }

    public static Map.Entry<String, String> parseParametersByEqual(String line) {
        String[] splitLine = line.split("=");
        return new AbstractMap
                .SimpleEntry<>(splitLine[KEY_INDEX].trim(), splitLine[VALUE_INDEX].trim());
    }

    public static String[] parseParametersBySemicolon(String line) {
        return line.split(";");
    }

    public static String parseExtension(String content) {
        String[] arr = content.split("\\.");
        return arr[arr.length - 1];
    }
}
