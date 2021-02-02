package webserver;

import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Parser;
import utils.UserService;

import java.util.Map;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserController() {
    }

    public static Response handle(RequestMessage requestMessage) {
        String url = Parser.parseURLFromRequestLine(requestMessage.getRequestLine());
        if (url.contains("/create")) {
            return create(requestMessage);
        }
        if (url.contains("/login")) {
            return login(requestMessage);
        }
        return ResponseNotFound.create();
    }

    public static Response create(RequestMessage requestMessage) {
        HttpMethod method = Parser.parseMethodFromRequestLine(requestMessage.getRequestLine());
        if (method == HttpMethod.POST) {
            String body = requestMessage.getRequestBody();
            Map<String, String> params = Parser.parseUserParams(body);
            UserService.insert(params);
            logger.debug("create user success");
            return ResponseFound.from("/");
        }
        return ResponseBadRequest.create();
    }

    public static Response login(RequestMessage requestMessage) {
        HttpMethod method = Parser.parseMethodFromRequestLine(requestMessage.getRequestLine());
        if (method == HttpMethod.POST) {
            String body = requestMessage.getRequestBody();
            Map<String, String> params = Parser.parseUserParams(body);
            if (UserService.isInValidUser(params)) {
                logger.debug("login failed");
                return ResponseFound.from("/user/login_failed.html");
            }
            logger.debug("login success");
            return ResponseFound.of("/", true);
        }
        return ResponseBadRequest.create();
    }
}
