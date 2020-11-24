package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.*;
import static java.util.Objects.requireNonNull;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL resource = getClassLoader().getResource(filePath);
        Path path = Paths.get(requireNonNull(resource, format("리소스가 존재하지 않습니다. filePath: %s", filePath)).toURI());
        return Files.readAllBytes(path);
    }

    private static ClassLoader getClassLoader() {
        return FileIoUtils.class.getClassLoader();
    }
}
