package webserver.controller;

import db.DataBase;
import model.User;
import webserver.domain.*;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!isRequestValid(httpRequest)) {
            httpResponse.send(HttpStatusCode.BAD_REQUEST, "userId, password가 필요합니다");
        }
        User findUser = DataBase.findUserById(httpRequest.getParameters().get("userId"));
        if (!findUser.getPassword().equals(httpRequest.getParameters().get("password"))) {
            loginFailed(httpResponse);
            return;
        }
        loginSucceeded(httpResponse);
    }

    private void loginSucceeded(HttpResponse httpResponse) {
        httpResponse.getHeaders().add(HttpHeader.SET_COOKIE, "logined=true; Path=/");
        httpResponse.getHeaders().add(HttpHeader.LOCATION, "/index.html");
        httpResponse.send(HttpStatusCode.FOUND);
    }

    private void loginFailed(HttpResponse httpResponse) {
        httpResponse.getHeaders().add(HttpHeader.SET_COOKIE, "logined=false; Path=/");
        httpResponse.getHeaders().add(HttpHeader.LOCATION, "/user/login_failed.html");
        httpResponse.send(HttpStatusCode.FOUND);
    }

    private boolean isRequestValid(HttpRequest httpRequest) {
        HttpParameters httpParameters = httpRequest.getParameters();
        if (httpParameters.contain("userId") && httpParameters.contain("password")) {
            return true;
        }
        return false;
    }
}
