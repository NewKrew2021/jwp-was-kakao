package webserver.controller;

import session.controller.SessionController;
import session.model.Session;
import user.controller.UserController;
import user.model.User;
import utils.FileIoUtils;
import webserver.model.*;

import java.util.Collection;
import java.util.Objects;

public class UserListController implements Controller {


    private static final String path = "/user/list";

    public String getPath() {
        return path;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        if (!isLogined(httpRequest)) {
            return loginFailedService();
        }

        return loginSuccessedService();
    }

    private boolean isLogined(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie(Cookie.SESSION);
        Session session = SessionController.findSession(sessionId);
        return !Objects.isNull(session)
                && !Objects.isNull(session.getAttribute(Session.USER_ID));
    }

    private HttpResponse loginSuccessedService() {
        HttpResponse httpResponse = new HttpResponse();

        Collection<User> users = UserController.findAll();

        String body = FileIoUtils.loadHandleBarFromClasspath(path, users);

        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, findContentType(path) + "; charset=utf-8");
        httpResponse.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length()));
        httpResponse.addHeader(HttpHeader.CONTENT_LOCATION, path);
        httpResponse.setBody(body);

        return httpResponse;
    }

    private HttpResponse loginFailedService() {
        HttpResponse httpResponse = new HttpResponse();
        String body = FileIoUtils.loadFileStringFromClasspath(FileIoUtils.TEMPLATES_PATH + FileIoUtils.LOGIN_PATH);

        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, "text/html; charset=utf-8");
        httpResponse.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length()));
        httpResponse.addHeader(HttpHeader.CONTENT_LOCATION, FileIoUtils.LOGIN_PATH);
        httpResponse.setBody(body);

        return httpResponse;
    }

    private String findContentType(String path) {
        if (path.startsWith("/css")) {
            return "text/css";
        }
        return "text/html";
    }

}
