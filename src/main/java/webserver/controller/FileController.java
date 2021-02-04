package webserver.controller;

import utils.FileIoUtils;
import webserver.model.HttpHeader;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;
import webserver.model.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController implements Controller {


    public String getPath() {
        return null;
    }


    @Override
    public HttpResponse service(HttpRequest httpRequest) {


        String path = httpRequest.getPath();
        if (path.equals(FileIoUtils.BASE_PATH)) {
            path = FileIoUtils.INDEX_PATH;
        }

        String body = null;
        try {
            body = FileIoUtils.loadFileStringFromClasspath(addBasePath(path));
        } catch (IOException e) {
        } catch (URISyntaxException e) {
        }

        HttpResponse httpResponse = new HttpResponse();

        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, findContentType(path) + "; charset=utf-8");
        httpResponse.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length()));
        httpResponse.addHeader(HttpHeader.CONTENT_LOCATION, path);
        httpResponse.setBody(body);

        return httpResponse;
    }

    private String findContentType(String path) {
        if (path.startsWith("/css")) {
            return "text/css";
        }
//        if (path.startsWith("/fonts")
//                || path.startsWith("/images")
//                || path.startsWith("/js")) {
//
//        }
        return "text/html";
    }

    private String addBasePath(String path) {
        if (path.startsWith("/css")
                || path.startsWith("/fonts")
                || path.startsWith("/images")
                || path.startsWith("/js")) {
            return FileIoUtils.STATIC_PATH + path;
        }
        return FileIoUtils.TEMPLATES_PATH + path;
    }
}
