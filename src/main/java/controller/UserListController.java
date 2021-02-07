package controller;

import exception.NotFoundException;
import exception.UnauthorizedException;
import model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import utils.ResourceLoader;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);
    private static final String PATH = "/user/list*/**";

    private static UserListController instance;

    private UserListController() {
    }

    public static UserListController getInstance() {
        if (instance == null) {
            instance = new UserListController();
        }
        return instance;
    }

    @Override
    public boolean match(String path) {
        PathMatcher matcher = new AntPathMatcher();
        return matcher.match(PATH, path);
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        // There is no matching action, so it does nothing.
        throw new NotFoundException("요청에 매칭되는 동작이 없습니다.");
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        try {
            validate(request);
            Resource resource = ResourceLoader.getDynamicPage("user/list");
            logger.debug("사용자 목록 페이지 로딩 성공");
            return HttpResponse.ok(resource);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return HttpResponse.redirect("/user/login.html");
        }
    }

    private void validate(HttpRequest request) {
        String cookie = request.getHeader("Cookie");
        if (!isLogined(cookie)) {
            String message = "비로그인 사용자입니다.";
            throw new UnauthorizedException(message);
        }
    }

    private boolean isLogined(String cookie) {
        return cookie != null && cookie.equals("logined=true");
    }
}
