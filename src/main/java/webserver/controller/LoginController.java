package webserver.controller;

import db.DataBase;
import model.User;
import webserver.domain.*;

public class LoginController extends AbstractController {
    @Override
    public HttpResponse doPost(HttpRequest httpRequest) throws Exception {
        User findUser = DataBase.findUserById(httpRequest.getParameter("userId"));
        if (findUser == null || !findUser.isValidPassword(httpRequest.getParameter("password"))) {
            return new HttpResponse.Builder()
                    .status(HttpStatusCode.FOUND)
                    .redirect("/user/login_failed.html")
                    .cookie(new Cookie("logined", "false", "Path", "/"))
                    .build();
        }
        return new HttpResponse.Builder()
                .status(HttpStatusCode.FOUND)
                .redirect("/index.html")
                .cookie(new Cookie("logined", "true", "Path", "/"))
                .build();
    }
}
