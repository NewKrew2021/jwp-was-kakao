package webserver;

import db.DataBase;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Parser;

import java.util.Map;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static Response handle(RequestMessage requestMessage) {
        HttpMethod method = Parser.parseMethodFromRequestLine(requestMessage.getRequestLine());
        String url = Parser.parseURLFromRequestLine(requestMessage.getRequestLine());

        if (method == HttpMethod.GET && url.contains("/create")) {
            Map<String, String> params = Parser.parseUserParams(url);
            User user = User.from(params);
            logger.debug("[" + method.name() + "] create " + user.toString());
            DataBase.addUser(user);
            return ResponseFound.from("/index.html");
        }
        if (method == HttpMethod.POST && url.contains("/create")) {
            String body = requestMessage.getRequestBody();
            Map<String, String> params = Parser.parseUserParams(body);
            User user = User.from(params);
            logger.debug("[" + method.name() + "] create " + user.toString());
            DataBase.addUser(user);
            return ResponseFound.from("/index.html");
        }
        return ResponseNotFound.create();
    }
}
