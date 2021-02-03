package webserver.controller;

import db.DataBase;
import model.User;
import webserver.RequestHandler;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;

public class UserLoginController implements Controller {

    private static final String LOGIN_FAIL_PATH = "/user/login_failed.html";

    private static final String path = "/user/login";

    public String getPath() {
        return path;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        String id = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        User user = DataBase.findUserById(id);

        String logined = "true";
        String location = RequestHandler.BASE_URL;

        if (user == null || !user.getPassword().equals(password)) {
            logined = "false";
            location = LOGIN_FAIL_PATH;
        }

        httpResponse.addHeader("Location", location);
        httpResponse.addCookie("logined", logined);
        httpResponse.sendRedirect();
    }
}
