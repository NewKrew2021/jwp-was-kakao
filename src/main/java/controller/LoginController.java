package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import response.HttpResponseStatusCode;
import java.util.Optional;

public class LoginController extends AbstractController {

    private static final String PASSWORD = "password";
    private static final String LOGIN_FAIL_PAGE = "/user/login_failed.html";
    private static final String USER_ID = "userId";
    private static final String INDEX_HTML = "/index.html";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter(USER_ID);
        String password = httpRequest.getParameter(PASSWORD);
        Optional<User> user = DataBase.findUserById(userId);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            httpResponse.addRedirectionLocationHeader(INDEX_HTML);
            httpResponse.addSetCookieHeader(true);
            httpResponse.send(HttpResponseStatusCode.FOUND);
            return;
        }
        httpResponse.addRedirectionLocationHeader(LOGIN_FAIL_PAGE);
        httpResponse.send(HttpResponseStatusCode.FOUND);
    }

}
