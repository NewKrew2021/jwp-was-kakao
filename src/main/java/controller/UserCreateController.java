package controller;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import exception.HttpException;
import model.User;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class UserCreateController extends AbstractController {

    private static final String INDEX_URL = "/index.html";
    private static final String USER_CREATE_URL = "/user/create";
    private static final String TEMPLATE = "/templates";


    //Todo Parameter에 값이 없을 경우 예외 처리
    @Override
    HttpResponse doGet(HttpRequest httpRequest) throws HttpException {
        return doPost(httpRequest);
    }

    @Override
    HttpResponse doPost(HttpRequest httpRequest) throws HttpException {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        DataBase.addUser(user);
        return new HttpResponse.Builder(HttpStatus.FOUND)
                .addHeader("Location", INDEX_URL)
                .build();
    }

    @Override
    public boolean isSupport(String path) {
        return path.equals(USER_CREATE_URL);
    }
}
