package utils;

import java.util.HashMap;
import java.util.Map;

public class FileLocationUtils {
    private static Map<String, String> contentLocation = new HashMap<>();

    static {
        contentLocation.put("ico","./templates");
        contentLocation.put("html","./templates");
    }

    public static String getContentLocation(String content) {
        return contentLocation
                .getOrDefault(ParseUtils.parseExtension(content), "./static") + content;
    }

}
