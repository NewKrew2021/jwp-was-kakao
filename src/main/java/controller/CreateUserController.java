package controller;

import annotation.web.Controller;
import annotation.web.RequestMapping;
import annotation.web.RequestMethod;
import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class CreateUserController extends AbstractController{

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if(httpRequest.getRequestMethod().equals(RequestMethod.POST)) {
            doPost(httpRequest,httpResponse);
            return;
        }
        if(httpRequest.getRequestMethod().equals(RequestMethod.GET)) {
            doGet(httpRequest,httpResponse);
            return;
        }
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> body = httpRequest.getBody();
        User user = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
        DataBase.addUser(user);
        httpResponse.response302Header("/index.html");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> params = httpRequest.getParams();
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        DataBase.addUser(user);
        httpResponse.response302Header("/index.html");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> params = httpRequest.getParams();
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        DataBase.addUser(user);
        httpResponse.response302Header("/index.html");
    }

}
