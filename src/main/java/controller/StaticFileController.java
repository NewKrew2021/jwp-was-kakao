package controller;

import exceptions.NoSuchResource;
import utils.FileIoUtils;
import webserver.http.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class StaticFileController extends Controller {

    @Override
    protected HttpResponse handleGet(HttpRequest httpRequest) {
        byte[] body = findResource(httpRequest.getPath());

        String contentType = ContentType.TEXT_HTML_UTF8;
        if (httpRequest.getPath().endsWith(".css")) {
            contentType = ContentType.TEXT_CSS_UTF8;
        }
        if (httpRequest.getPath().endsWith(".js")) {
            contentType = ContentType.APPLICATION_JAVASCRIPT;
        }
        return new HttpResponseBuilder().with200OK()
                .withBody(body)
                .withContentType(contentType)
                .build();
    }

    private byte[] findResource(String path) {
        return Stream.of("./templates", "./static")
                .map(resourceRoot -> tryFindResource(resourceRoot, path))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new NoSuchResource(path));
    }

    private Optional<byte[]> tryFindResource(String resourceRoot, String path) {
        String templatePath = Paths.get(resourceRoot, path).toString();
        try {
            return Optional.of(FileIoUtils.loadFileFromClasspath(templatePath));
        } catch (IOException | URISyntaxException | NoSuchResource e) {
            return Optional.empty();
        }
    }
}
