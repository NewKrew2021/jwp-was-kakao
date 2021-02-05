package controller;

import exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.UserService;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserLoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    private static final String PATH = "/user/login";


    private static UserLoginController instance;

    private UserLoginController() {
    }

    public static UserLoginController getInstance() {
        if (instance == null) {
            instance = new UserLoginController();
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
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        if (UserService.isInValidUser(userId, password)) {
            logger.debug("[ID: {}] 유효하지 않은 사용자입니다.", userId);
            return HttpResponse.redirect("/user/login_failed.html");
        }
        logger.debug("[ID: {}] 로그인 성공", userId);
        HttpResponse response = HttpResponse.redirect("/");
        response.addHeader("Set-Cookie", "logined=true; Path=/");
        return response;
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        // There is no matching action, so it does nothing.
        throw new NotFoundException("요청에 매칭되는 동작이 없습니다.");
    }
}
