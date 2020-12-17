package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    private final static Map<String, String> FILE_SUFFIX_CONTENT_TYPE_MAP = new HashMap<>();
    private static final String UNKNOWN_CONTENT_TYPE = "application/octet-stream";

    static {
        // from:
        // https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
        // https://www.freeformatter.com/mime-types-list.html

        FILE_SUFFIX_CONTENT_TYPE_MAP.put(".html", "text/html; charset=utf-8");
        FILE_SUFFIX_CONTENT_TYPE_MAP.put(".css", "text/css; charset=utf-8");

        FILE_SUFFIX_CONTENT_TYPE_MAP.put(".svg", "image/svg+xml");
        FILE_SUFFIX_CONTENT_TYPE_MAP.put(".png", "image/png");

        FILE_SUFFIX_CONTENT_TYPE_MAP.put(".woff", "font/woff");
        FILE_SUFFIX_CONTENT_TYPE_MAP.put(".woff2", "font/woff2");
        FILE_SUFFIX_CONTENT_TYPE_MAP.put(".eot", "application/vnd.ms-fontobject");
        FILE_SUFFIX_CONTENT_TYPE_MAP.put(".ttf", "font/ttf");

        FILE_SUFFIX_CONTENT_TYPE_MAP.put(".js", "application/js");
    }

    public static String guessContentType(String filePath) {
        int comma = filePath.lastIndexOf(".");
        if (comma > 0) {
            String ext = filePath.substring(comma);
            return FILE_SUFFIX_CONTENT_TYPE_MAP.getOrDefault(ext, UNKNOWN_CONTENT_TYPE);
        }

        return UNKNOWN_CONTENT_TYPE;
    }

}
