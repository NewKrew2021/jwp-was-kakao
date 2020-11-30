package utils;

import exceptions.NoSuchResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, NoSuchResource, URISyntaxException {
        Optional<URL> url = Optional.ofNullable(FileIoUtils.class.getClassLoader().getResource(filePath));
        Path path = Paths.get(url.orElseThrow(() -> new NoSuchResource(filePath)).toURI());
        return Files.readAllBytes(path);
    }
}
