package webserver.controller;

import db.DataBase;
import model.User;
import org.checkerframework.checker.units.qual.C;
import webserver.domain.*;

public class LoginController extends AbstractController {
    @Override
    public HttpResponse doPost(HttpRequest httpRequest) throws Exception {
        User findUser = DataBase.findUserById(httpRequest.getParameter("userId"));
        if (findUser == null || !findUser.isValidPassword(httpRequest.getParameter("password"))) {
            Cookie cookie = new Cookie("logined=false");
            cookie.setPath("/");
            return new HttpResponse.Builder()
                    .status(HttpStatusCode.FOUND)
                    .redirect("/user/login_failed.html")
                    .cookie(cookie)
                    .build();
        }
        Cookie cookie = new Cookie("logined=true");
        cookie.setPath("/");
        return new HttpResponse.Builder()
                .status(HttpStatusCode.FOUND)
                .redirect("/index.html")
                .cookie(cookie)
                .build();
    }
}
