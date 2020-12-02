package webserver.http.utils;

public class FileExtentions {
    public static String fromPath(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }
}
