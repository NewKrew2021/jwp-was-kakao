package controller;

import annotation.web.Controller;
import request.HttpRequest;
import response.HttpResponse;

@Controller(path = "/user/create")
public class CreateUserController extends AbstractController{

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

}
