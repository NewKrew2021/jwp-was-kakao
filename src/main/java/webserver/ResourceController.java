package webserver;

import model.RequestMessage;
import model.Response;
import model.ResponseOK;
import utils.FileIoUtils;
import utils.Parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ResourceController {
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";
    private static final List<String> templatesResource = Arrays.asList("/index", "/favicon");
    private static final List<String> staticResource = Arrays.asList("/css", "/fonts", "/images", "/js");

    private ResourceController() {
    }

    public static boolean hasResource(String url) {
        return Stream.concat(templatesResource.stream(), staticResource.stream())
                .anyMatch(url::contains);
    }

    public static Response handle(RequestMessage requestMessage) throws IOException, URISyntaxException {
        String url = Parser.parseURLFromRequestLine(requestMessage.getRequestLine());
        String contentType = Parser.parseContentTypeFromRequestHeader(requestMessage.getRequestHeader());

        byte[] body = FileIoUtils.loadFileFromClasspath(getResourcePath(url));
        return ResponseOK.of(contentType, body);
    }

    private static String getResourcePath(String url) {
        return isTemplateResource(url) ? TEMPLATES_PATH + url : STATIC_PATH + url;
    }

    private static boolean isTemplateResource(String url) {
        return templatesResource.stream()
                .anyMatch(url::contains);
    }
}
