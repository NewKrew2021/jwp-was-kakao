package webserver;

import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Parser;
import utils.ResourceLoader;

import java.util.Map;

public class ResourceController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private ResourceController() {
    }

    public static Response handle(RequestMessage requestMessage) {
        HttpMethod httpMethod = Parser.parseMethodFromRequestLine(requestMessage.getRequestLine());
        if (httpMethod != HttpMethod.GET) {
            return ResponseBadRequest.create();
        }

        String url = Parser.parseURLFromRequestLine(requestMessage.getRequestLine());
        if (url.contains("list")) {
            return list(requestMessage);
        }
        return load(requestMessage);
    }

    private static Response list(RequestMessage requestMessage) {
        try {
            validate(requestMessage.getRequestHeader());
        } catch (RuntimeException e) {
            return ResponseFound.from("/user/login.html");
        }
        byte[] page = ResourceLoader.getDynamicPage("user/list");
        logger.debug("load dynamic page success");
        String contentType = Parser.parseContentTypeFromRequestHeader(requestMessage.getRequestHeader());
        return ResponseOK.of(contentType, page);
    }

    private static void validate(Map<String, String> requestHeader) {
        String cookie = Parser.parseCookie(requestHeader);
        if (!isLogined(cookie)) {
            String message = "로그인이 필요합니다.";
            logger.debug(message);
            throw new RuntimeException(message);
        }
    }

    private static boolean isLogined(String cookie) {
        return cookie != null && cookie.equals("logined=true");
    }

    private static Response load(RequestMessage requestMessage) {
        String url = Parser.parseURLFromRequestLine(requestMessage.getRequestLine());
        String contentType = Parser.parseContentTypeFromRequestHeader(requestMessage.getRequestHeader());
        byte[] body = ResourceLoader.getBytes(url);
        logger.debug("load success");
        return ResponseOK.of(contentType, body);
    }

    public static boolean isResource(String url) {
        int idx = url.lastIndexOf('.');
        return idx != -1 && idx < url.length() - 1;
    }
}
