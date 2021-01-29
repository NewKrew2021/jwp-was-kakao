package webserver;

import dto.HttpRequest;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController {

    public static byte[] get(HttpRequest request) {
        try {
            return FileIoUtils.loadFileFromClasspath("templates/" + request.getUri());
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage().getBytes();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return e.getMessage().getBytes();
        }
    }
}
