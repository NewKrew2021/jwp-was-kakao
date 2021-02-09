package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import exception.HttpException;
import utils.FileIoUtils;

import static domain.HttpHeader.HEADER_CONTENT_LENGTH;

public class FileController extends AbstractController {
    private static final String ROOT_URL = "/";
    private static final String INDEX_URL = "/index.html";
    private static final String TEMPLATE_BASE_PATH = "./templates";
    private static final String STATIC_BASE_PATH = "./static";

    private static final String EXTENSION_HTML = ".html";
    private static final String EXTENSION_CSS = ".css";
    private static final String EXTENSION_ICO = ".ico";

    private static final String CONTENT_TYPE_CSS = "text/css; charset=utf-8";
    private static final String CONTENT_TYPE_HTML = "text/html; charset=utf-8";

    @Override
    HttpResponse doGet(HttpRequest httpRequest) throws HttpException {
        String path = httpRequest.getPath();
        if(path.equals(ROOT_URL)) {
            path = INDEX_URL;
        }

        return new HttpResponse.Builder()
                .ok(FileIoUtils.loadFileFromClasspath(addBasePath(path)))
                .addHeader(HEADER_CONTENT_LENGTH, getContentType(path))
                .build();
    }

    @Override
    HttpResponse doPost(HttpRequest httpRequest) throws HttpException {
        return doGet(httpRequest);
    }

    @Override
    public boolean isSupport(String path) {
        return true;
    }

    private String addBasePath(String path) {
        if (path.endsWith(EXTENSION_HTML) || path.endsWith(EXTENSION_ICO)) {
            return TEMPLATE_BASE_PATH + path;
        }
        return STATIC_BASE_PATH + path;
    }

    private String getContentType(String path) {
        if (path.endsWith(EXTENSION_CSS)) {
            return CONTENT_TYPE_CSS;
        }
        return CONTENT_TYPE_HTML;
    }
}
