package controller;

import exceptions.NoSuchResource;
import utils.FileIoUtils;
import webserver.http.HttpCode;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class StaticFileController implements Controller {

    @Override
    public HttpResponse handleRequest(HttpRequest httpRequest) throws NoSuchResource {
        byte[] body = findResource(httpRequest.getPath());
        return new HttpResponse(HttpCode._200_OK, body);
    }

    private byte[] findResource(String path) throws NoSuchResource {
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
