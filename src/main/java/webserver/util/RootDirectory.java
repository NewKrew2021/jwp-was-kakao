package webserver.util;

import org.apache.commons.io.FilenameUtils;

public class RootDirectory {
    public static String get(String path) {
        String extension = FilenameUtils.getExtension(path);
        if (extension.equals("html") || extension.equals("ico")) {
            return "./templates";
        }
        return "./static";
    }
}
