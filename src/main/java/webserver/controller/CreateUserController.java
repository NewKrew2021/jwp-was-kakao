package webserver.controller;

import db.DataBase;
import model.User;
import webserver.domain.HttpHeader;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            User user = getUser(httpRequest);
            DataBase.addUser(user);
            httpResponse.getHeaders().add(HttpHeader.LOCATION, "/index.html");
            httpResponse.send(HttpStatusCode.FOUND);
        } catch (NullPointerException e) {
            httpResponse.send(HttpStatusCode.BAD_REQUEST, "User 등록하는데 필요한 파라미터가 부족합니다");
        }
    }

    private User getUser(HttpRequest httpRequest) {
        return new User(
                httpRequest.getParameters().get("userId"),
                httpRequest.getParameters().get("password"),
                httpRequest.getParameters().get("name"),
                httpRequest.getParameters().get("email")
        );
    }
}
