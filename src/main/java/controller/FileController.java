package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import exception.FileIOException;
import exception.NoSuchFileException;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends AbstractController {
    private static final String ROOT_URL = "/";
    private static final String INDEX_URL = "/index.html";
    private static final String TEMPLATE_BASE_PATH = "./templates";
    private static final String STATIC_BASE_PATH = "./static";

    private static final String EXTENSION_HTML = ".html";
    private static final String EXTENSION_ICO = ".ico";

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws URISyntaxException, NoSuchFileException, IOException {
        String path = httpRequest.getPath();
        if(path.equals(ROOT_URL)) {
            path = INDEX_URL;
        }

        httpResponse.ok(path)
                .body(FileIoUtils.loadFileFromClasspath(addBasePath(path)))
                .send();
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException, NoSuchFileException {
        doGet(httpRequest, httpResponse);
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
}
