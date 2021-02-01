package webserver;

import model.RequestMessage;
import model.Response;
import model.ResponseOK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.Parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ResourceController {
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";
    private static final List<String> templatesResource = loadFileList(TEMPLATES_PATH);
    private static final List<String> staticResource = loadFileList(STATIC_PATH);
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private static List<String> loadFileList(String filePath) {
        try {
            return FileIoUtils.loadFileList(filePath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    private ResourceController() {
    }

    public static boolean hasResource(String url) {
        return Stream.concat(templatesResource.stream(), staticResource.stream())
                .anyMatch(resource -> resource.contains(url));
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
                .anyMatch(resource -> resource.contains(url));
    }
}
