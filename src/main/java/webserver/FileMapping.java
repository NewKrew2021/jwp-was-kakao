package webserver;

import model.FileExtension;

import java.util.HashMap;
import java.util.Map;

public class FileMapping {
    public static Map<String, FileExtension> fileExtension = new HashMap<>();

    static {
        fileExtension.put(".html", new FileExtension(".html", "templates/", "text/html; charset=utf-8"));
        fileExtension.put(".css", new FileExtension(".css", "static/", "text/css"));
        fileExtension.put(".js", new FileExtension(".js", "static/", "text/javascript"));
    }

    public static boolean isFileRequest(String url) {
        return fileExtension.keySet().stream()
                .anyMatch(extension -> url.endsWith(extension));
    }

    public static String getFileURL(String path) {
        FileExtension file = findFile(path);
        return file.getLocation() + path;
    }

    public static String getContentType(String path) {
        FileExtension file = findFile(path);
        return file.getContentType();
    }

    public static FileExtension findFile(String path) {
        String key = fileExtension.keySet().stream()
                .filter(extension -> path.endsWith(extension))
                .findFirst()
                .orElse(null);
        return fileExtension.get(key);
    }
}
