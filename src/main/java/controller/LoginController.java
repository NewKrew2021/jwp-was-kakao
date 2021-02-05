package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class LoginController extends AbstractController {



    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = DataBase.findUserById(httpRequest.getParameter("userId"));
        login.updateLoginState(user.getUserId(), user.getPassword(), httpRequest.getParameter("password"));

        if (login.isLogin()) {
            httpResponse.login();
            httpResponse.sendRedirect(INDEX_HTML);
            return;
        }

        httpResponse.sendRedirect(LOGIN_FAIL_PAGE);
    }
}
