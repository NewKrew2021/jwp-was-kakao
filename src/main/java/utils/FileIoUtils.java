package utils;

import exception.utils.NoFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtils.class);

    public static byte[] loadFileFromClasspath(String filePath) throws NoFileException {
        log.debug("reading file {}", filePath);
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new NoFileException();
        }
    }
}
