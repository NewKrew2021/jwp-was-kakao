package controller;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateController extends Controller{

    public static RequestHandler htmlHandler = (request, dos) -> {
        try {
            String path = request.getUri();
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + path);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    };
}
