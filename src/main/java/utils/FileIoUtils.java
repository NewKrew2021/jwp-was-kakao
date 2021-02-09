package utils;

import exception.HttpException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws HttpException {
        byte[] file = null;
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            file = Files.readAllBytes(path);
        } catch (NullPointerException e) {
            throw new HttpException(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (URISyntaxException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST);
        }
        return file;
    }
}
