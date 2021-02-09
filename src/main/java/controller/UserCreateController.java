package controller;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;

import java.io.IOException;

public class UserCreateController extends AbstractController {

    private static final String INDEX_URL = "/index.html";
    private static final String USER_CREATE_URL = "/user/create";
    private static final String TEMPLATE = "/templates";


    //Todo Parameter에 값이 없을 경우 예외 처리
    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        doPost(httpRequest, httpResponse);
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        DataBase.addUser(user);
        httpResponse.redirect(INDEX_URL).send();
    }

    @Override
    public boolean isSupport(String path) {
        return path.equals(USER_CREATE_URL);
    }
}
