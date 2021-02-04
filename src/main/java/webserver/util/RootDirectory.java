package webserver.util;

import org.apache.commons.io.FilenameUtils;

public class RootDirectory {

    private static final String TEMPLATES = "./templates";
    private static final String STATIC = "./static";
    private static final String HTML = "html";
    private static final String ICO = "ico";

    public static String get(String path) {
        String extension = FilenameUtils.getExtension(path);
        if (extension.equals(HTML) || extension.equals(ICO)) {
            return TEMPLATES;
        }
        return STATIC;
    }
}
