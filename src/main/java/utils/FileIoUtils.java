package utils;

import exception.InvalidFileException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String NOT_EXIST_MESSAGE = "(이)가 존재하지 않습니다.";

    private FileIoUtils() {
    }

    public static byte[] loadFileFromClasspath(String filePath) {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (resource == null) {
            throw new InvalidFileException(filePath + NOT_EXIST_MESSAGE);
        }
        try {
            Path path = Paths.get(resource.toURI());
            return Files.readAllBytes(path);
        } catch (IOException | URISyntaxException e) {
            throw new InvalidFileException(e.getMessage());
        }
    }
}
