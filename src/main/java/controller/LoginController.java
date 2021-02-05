package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.Login;
import model.User;

public class LoginController extends AbstractController {

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = DataBase.findUserById(httpRequest.getParameter("userId"));
        Login login = httpResponse.getLogin();
        login.updateLoginState(user.getUserId(), user.getPassword(), httpRequest.getParameter("password"));

        if (login.isLogin()) {
            httpResponse.sendRedirect(INDEX_HTML);
            return;
        }

        httpResponse.sendRedirect(LOGIN_FAIL_PAGE);
    }
}
