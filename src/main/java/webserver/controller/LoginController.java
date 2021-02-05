package webserver.controller;

import db.DataBase;
import model.User;
import webserver.domain.*;
import webserver.exceptions.InvalidRequestException;

public class LoginController extends AbstractController {
    private static final String INVALID_REQUEST_MESSAGE = "userId, password가 필요합니다";
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User findUser = DataBase.findUserById(httpRequest.getParameters().get("userId"));
        if(findUser == null){
            sendLoginFailed(httpResponse);
            return;
        }
        if (!findUser.getPassword().equals(httpRequest.getParameters().get("password"))) {
            sendLoginFailed(httpResponse);
            return;
        }
        sendLoginSucceeded(httpResponse);
    }

    @Override
    public void validatePostRequest(HttpRequest request, HttpResponse response) throws InvalidRequestException {
        HttpParameters httpParameters = request.getParameters();
        if (httpParameters.contain("userId") && httpParameters.contain("password")) {
            return;
        }
        response.send(HttpStatusCode.BAD_REQUEST, INVALID_REQUEST_MESSAGE);
        throw new InvalidRequestException(INVALID_REQUEST_MESSAGE);
    }

    private void sendLoginSucceeded(HttpResponse httpResponse) {
        httpResponse.getHeaders().add(HttpHeader.SET_COOKIE, "logined=true; Path=/");
        httpResponse.getHeaders().add(HttpHeader.LOCATION, "/index.html");
        httpResponse.send(HttpStatusCode.FOUND);
    }

    private void sendLoginFailed(HttpResponse httpResponse) {
        httpResponse.getHeaders().add(HttpHeader.SET_COOKIE, "logined=false; Path=/");
        httpResponse.getHeaders().add(HttpHeader.LOCATION, "/user/login_failed.html");
        httpResponse.send(HttpStatusCode.FOUND);
    }

}
