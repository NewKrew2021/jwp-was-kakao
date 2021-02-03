package webserver;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import domain.URLMapper;

public class UserLoginController extends AbstractController {

    private static final String LOGIN_FAIL_PATH = "/user/login_failed.html";

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String longinedCookie = "logined=false; Path=/";
        String location = LOGIN_FAIL_PATH;
        if (DataBase.login(httpRequest.getParameter("userId"), httpRequest.getParameter("password"))) {
            longinedCookie = "logined=true; Path=/";
            location = URLMapper.INDEX_URL;
        }

        httpResponse.addHeader("Set-Cookie", longinedCookie);
        httpResponse.sendRedirect(location);
    }
}
