package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Parser;

import java.util.List;
import java.util.Map;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static void handleUser(HttpMethod method, String url, String body) {
        if(method == HttpMethod.GET && url.contains("/create")) {
            Map<String, String> params = Parser.parseUserParams(url);
            User user = User.from(params);
            logger.debug(user.toString());
            DataBase.addUser(user);
        }
        if(method == HttpMethod.POST && url.contains("/create")) {
            Map<String, String> params = Parser.parseUserParams(body);
            User user = User.from(params);
            logger.debug(user.toString());
            DataBase.addUser(user);
        }
    }
}
