package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Parser;

import java.util.Map;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static String handle(HttpMethod method, String url, String body) {
        if (method == HttpMethod.GET && url.contains("/create")) {
            Map<String, String> params = Parser.parseUserParams(url);
            User user = User.from(params);
            logger.debug("[" + method.name() + "] create " + user.toString());
            DataBase.addUser(user);
            return "./templates/index.html";
        }
        if (method == HttpMethod.POST && url.contains("/create")) {
            Map<String, String> params = Parser.parseUserParams(body);
            User user = User.from(params);
            logger.debug("[" + method.name() + "] create " + user.toString());
            DataBase.addUser(user);
            return "./templates/index.html";
        }
        return "./templates/error.html";
    }
}
