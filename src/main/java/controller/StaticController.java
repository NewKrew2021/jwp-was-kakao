package controller;

import request.HttpRequest;
import response.HttpResponse;
import response.HttpResponseStatusCode;
import utils.FileIoUtils;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController extends AbstractController {

    private static final String TEMPLATES_FILE_PATH = "src/main/resources/templates";
    private static final String STATIC_FILE_PATH = "src/main/resources/static";
    private static final String TEMPLATES_PATH_PREFIX = "./templates";
    private static final String STATIC_PATH_PREFIX = "./static";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (fileExist(TEMPLATES_FILE_PATH, httpRequest.getPath())) {
            makeResponse(TEMPLATES_PATH_PREFIX, httpRequest.getPath(), httpResponse);
            return;
        }
        if (fileExist(STATIC_FILE_PATH, httpRequest.getPath())) {
            makeResponse(STATIC_PATH_PREFIX, httpRequest.getPath(), httpResponse);
        }
    }

    private boolean fileExist(String filePath, String uri) {
        return new File(filePath + uri).exists();
    }

    private void makeResponse(String filePathPrefix, String path, HttpResponse httpResponse) {
        byte[] body;
        try {
            body = FileIoUtils.loadFileFromClasspath(filePathPrefix + path);
            httpResponse
                    .addContentTypeHeader(path)
                    .addContentLengthHeader(body.length)
                    .addResponseBody(body)
                    .send(HttpResponseStatusCode.OK)
                    .build();
        } catch (IOException | URISyntaxException e) {
            httpResponse
                    .send(HttpResponseStatusCode.NOT_FOUND)
                    .build();
        }
    }

}
