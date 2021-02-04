package webserver;

import model.File;

import java.util.HashMap;
import java.util.Map;

public class FileMapping {
    public static Map<String, File> fileExtension = new HashMap<>();

    static {
        fileExtension.put(".html", new File(".html", "templates/", "text/html; charset=utf-8"));
        fileExtension.put(".css", new File(".css", "static/", "text/css"));
        fileExtension.put(".js", new File(".js", "static/", "text/javascript"));
    }

    public static boolean isFileRequest(String url) {
        return fileExtension.keySet().stream()
                .anyMatch(extension -> url.endsWith(extension));
    }

    public static String getFileURL(String path) {
        File file = findFile(path);
        return file.getLocation() + path;
    }

    public static String getContentType(String path) {
        File file = findFile(path);
        return file.getContentType();
    }

    public static File findFile(String path) {
        String key = fileExtension.keySet().stream()
                .filter(extension -> path.endsWith(extension))
                .findFirst()
                .orElse(null);
        return fileExtension.get(key);
    }
}
