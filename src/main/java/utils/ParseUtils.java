package utils;

public class ParseUtils {
    public static String parseExtension(String content) {
        String[] arr = content.split("\\.");
        return arr[arr.length - 1];
    }
}
