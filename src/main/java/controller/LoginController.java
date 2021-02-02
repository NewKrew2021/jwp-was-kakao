package controller;

import db.DataBase;
import request.HttpRequest;
import response.HttpResponse;

import java.util.Map;

public class LoginController extends AbstractController{
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String,String> params = httpRequest.getBody();
        String userId = params.get("userId");
        String password = params.get("password");
        if(DataBase.findUserById(userId).getPassword().equals(password)) {
            httpResponse.response302Header("/index.html" , "true");
        }
        httpResponse.response302Header("/user/login_failed.html" , "false");
    }

}
