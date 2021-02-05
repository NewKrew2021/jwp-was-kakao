package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    private FileIoUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static byte[] loadFileFromClasspath(String filePath) throws URISyntaxException, IOException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

}
