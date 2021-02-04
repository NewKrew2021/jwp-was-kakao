package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;

public class FileIoUtils {

    private FileIoUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static boolean isExistFile(String filePath) throws URISyntaxException {
        return FileIoUtils.class.getClassLoader().getResource(filePath) != null;
    }


}
