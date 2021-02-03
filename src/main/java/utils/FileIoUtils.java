package utils;

import exceptions.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            logger.debug("FILEPATH:" + filePath);
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }

    public static String getFileMemeType(String filePath) {
        try {
            logger.debug("FILEPATH:" + filePath);
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.probeContentType(path);
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }
}
