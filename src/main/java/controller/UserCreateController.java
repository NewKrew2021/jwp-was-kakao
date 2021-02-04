package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.UserService;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserCreateController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);
    private static final String PATH = "/user/create";

    private static UserCreateController instance;

    private UserCreateController() {
    }

    public static UserCreateController getInstance() {
        if (instance == null) {
            instance = new UserCreateController();
        }
        return instance;
    }

    @Override
    public boolean match(String path) {
        return PATH.equals(path);
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.insert(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email")
        );
        logger.debug("create user success");
        httpResponse.sendRedirect("/");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        // There is no matching action, so it does nothing.
    }
}
