package controller;

import db.DataBase;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.util.Optional;

public class ListUserController extends AbstractController{

    private static final String COOKIE = "Cookie";
    private static final String COOKIE_TRUE_VALUE = "logined=true";
    private static final String MAIN_PAGE = "/user/login.html";
    private static final String FALSE = "false";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if(isLogin(httpRequest.getParam(COOKIE))) {
            try {
                httpResponse.responseTemplate(DataBase.findAll());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        httpResponse.response302Header(MAIN_PAGE, FALSE);
    }
    boolean isLogin(Optional<String> cookieValue) {
        return cookieValue.isPresent() && cookieValue.get().equals(COOKIE_TRUE_VALUE);
    }


}
