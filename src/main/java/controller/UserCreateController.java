package controller;

import exception.NotFoundException;
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
    public HttpResponse doPost(HttpRequest request) {
        UserService.insert(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        logger.debug("[ID: {}] 회원가입 성공", request.getParameter("userId"));
        return HttpResponse.redirect("/");
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        // There is no matching action, so it does nothing.
        throw new NotFoundException("요청에 매칭되는 동작이 없습니다.");
    }
}
