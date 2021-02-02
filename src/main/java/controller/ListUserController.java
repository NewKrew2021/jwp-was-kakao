package controller;

import db.DataBase;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.util.Optional;

public class ListUserController extends AbstractController{
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if(isLogin(httpRequest.getParam("Cookie"))) {
            try {
                httpResponse.responseBodyTemplate(DataBase.findAll());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        httpResponse.response302Header("/user/login.html", "false");
    }
    boolean isLogin(Optional<String> cookieValue) {
        return cookieValue.isPresent() && cookieValue.get().equals("logined=true");
    }


}
