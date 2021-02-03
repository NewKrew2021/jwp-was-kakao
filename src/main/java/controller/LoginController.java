package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import java.util.Optional;

public class LoginController extends AbstractController{

    private static final String PASSWORD = "password";
    private static final String LOGIN_FAIL_PAGE = "/user/login_failed.html";
    private static final String USER_ID = "userId";
    private static final String INDEX_HTML = "/index.html";
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter(USER_ID);
        String password = httpRequest.getParameter(PASSWORD);
        Optional<User> user =  DataBase.findUserById(userId);
        if(user.isPresent() && user.get().getPassword().equals(password)) {
            httpResponse.response302Header(INDEX_HTML, TRUE);
        }
        httpResponse.response302Header(LOGIN_FAIL_PAGE, FALSE);
    }

}
