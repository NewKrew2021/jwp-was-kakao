package model;

import java.util.HashMap;
import java.util.Map;

public class ContentType {
    private static final Map<String, String> contentType = new HashMap<>();
    static {
        contentType.put(".js", "text/js");
        contentType.put(".html", "text/html");
        contentType.put(".css", "text/css");
        contentType.put(".woff", "application/font-woff");
        contentType.put(".ttf", "application/x-font-ttf");
        contentType.put(".ico", "image/x-icon");
    }

    public String get(String extension) {
        return contentType.get(extension);
    }
}
