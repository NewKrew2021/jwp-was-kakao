package application;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;

public class LoginController extends AbstractController {
    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = DataBase.findUserById(httpRequest.getParameter("userId"));
        // 아이디가 없습니다.
        boolean isLoginSuccess = user.validatePassword(httpRequest.getParameter("password"));
        // 패스워드가 잘못됐습니다.
        if (isLoginSuccess) {
            httpResponse.sendRedirect("/index.html");
            httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
            return;
        }
        httpResponse.sendRedirect("/user/login_failed.html");
        httpResponse.addHeader("Set-Cookie", "logined=false;");
    }
}
