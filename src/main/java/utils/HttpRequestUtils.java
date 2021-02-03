package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequestUtils {
    public static String extractPath(String uri) {
        return uri.split("\\?")[0];
    }

    public static Optional<String> extractParams(String uri) {
        String[] tmp = uri.split("\\?");
        if (tmp.length <= 1) {
            return Optional.empty();
        }
        return Optional.of(tmp[1]);
    }

    public static Map<String, String> requestStringToMap(String line) {
        Map<String, String> result = new HashMap<>();
        String[] splitString = line.split("&");
        for (String pair : splitString) {
            String[] splitPair = pair.split("=");
            result.put(splitPair[0], splitPair[1]);
        }
        return result;
    }

}
