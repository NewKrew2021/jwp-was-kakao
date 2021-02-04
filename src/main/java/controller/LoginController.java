package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;

public class LoginController extends AbstractController {



    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        boolean login = DataBase.isPossibleLogin(httpRequest.getParameter("userId"), httpRequest.getParameter("password"));

        if (login) {
            httpResponse.login();
            httpResponse.sendRedirect(INDEX_HTML);
            return;
        }

        httpResponse.sendRedirect(LOGIN_FAIL_PAGE);
    }
}
