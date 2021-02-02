package webserver;

import dto.HttpRequest;
import dto.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class FileController {

    public static HttpResponse get(HttpRequest request) {
        try {
            HttpResponse response = new HttpResponse("HTTP/1.1 200 OK");
            response.setBody(FileIoUtils.loadFileFromClasspath("templates/" + request.getUri()));
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return new HttpResponse("HTTP/1.1 500 INTERNAL SERVER ERROR", e.getMessage().getBytes(StandardCharsets.UTF_8));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return new HttpResponse("HTTP/1.1 500 INTERNAL SERVER ERROR", e.getMessage().getBytes(StandardCharsets.UTF_8));
        }
    }
}