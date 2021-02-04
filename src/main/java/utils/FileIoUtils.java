package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static final String TEMPLATES_PATH = "./templates";
    public static final String STATIC_PATH = "./static";

    public static final String BASE_PATH = "/";
    public static final String INDEX_PATH = "/index.html";
    public static final String LOGIN_PATH = "/user/login.html";
    public static final String LOGIN_FAIL_PATH = "/user/login_failed.html";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static String loadFileStringFromClasspath(String filePath) throws IOException, URISyntaxException {
        return new String(loadFileFromClasspath(filePath), StandardCharsets.UTF_8);
    }
}
