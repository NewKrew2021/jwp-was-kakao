package controller;

import service.UserService;
import webserver.http.HttpCode;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Map;

public class UserCreateController extends Controller {
    public static final String PATH = "/user/create";

    @Override
    public HttpResponse handleGet(HttpRequest httpRequest) {
        Map<String, String> parameters = httpRequest.getParameters();
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");
        UserService.addNewUser(userId, password, name, email);

        return new HttpResponse(HttpCode._200);
    }
}
