package utils;

import exception.ExceptionHandler;
import exception.FileIOException;
import exception.NoSuchFileException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws FileIOException, URISyntaxException, NoSuchFileException {
        byte[] file = null;
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            file = Files.readAllBytes(path);
        } catch (NullPointerException e) {
            throw new NoSuchFileException();
        } catch (IOException e) {
            throw new FileIOException(filePath);
        }
        return file;
    }
}
