package model;

import exception.http.IllegalExtensionException;

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

    public static String of(String extension) {
        String mimeType = contentType.get(extension);
        if (mimeType == null) {
            throw new IllegalExtensionException();
        }
        return mimeType;
    }
}
