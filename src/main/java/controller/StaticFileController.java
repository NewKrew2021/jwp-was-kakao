package controller;

import exceptions.NoSuchResource;
import utils.FileIoUtils;
import webserver.http.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class StaticFileController extends Controller {

    private static final String PATH = "/*";
    private static final String EXTENSION_CSS = "css";
    private static final String EXTENSION_JS = "js";
    private static final Map<String, String> contentTypeResolver = new HashMap<>();

    static {
        contentTypeResolver.put(EXTENSION_CSS, ContentType.TEXT_CSS_UTF8);
        contentTypeResolver.put(EXTENSION_JS, ContentType.APPLICATION_JAVASCRIPT);
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    protected HttpResponse handleGet(HttpRequest httpRequest) {
        byte[] body = findResource(httpRequest.getPath());

        String fileExtension = getExtensionOfFile(httpRequest.getPath());
        String contentType = getContentTypeForExtension(fileExtension);

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

    private String getExtensionOfFile(String file) {
        int i = file.lastIndexOf('.');
        if (i > 0) {
            return file.substring(i + 1);
        }
        return "";
    }

    private String getContentTypeForExtension(String extension) {
        String contentType = ContentType.TEXT_HTML_UTF8;
        if (contentTypeResolver.containsKey(extension)) {
            contentType = contentTypeResolver.get(extension);
        }

        return contentType;
    }
}
