package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String TEMPLATE_PREFIX = "templates";
    private static final String STATIC_PREFIX = "static";

    public static byte[] readStaticHttpFile(String path) throws IOException, URISyntaxException {
        if (path.endsWith(".html") || path.endsWith(".ico")) {
            return loadFileFromClasspath(TEMPLATE_PREFIX + path);
        }
        return loadFileFromClasspath(STATIC_PREFIX + path);
    }

    private static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }
}
