package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String BASE_PATH = "./templates";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL resource = FileIoUtils.class.getClassLoader().getResource(BASE_PATH + filePath);
        if (resource == null) {
            throw new IOException("존재하지 않는 파일입니다.");
        }
        Path path = Paths.get(resource.toURI());
        return Files.readAllBytes(path);
    }
}
