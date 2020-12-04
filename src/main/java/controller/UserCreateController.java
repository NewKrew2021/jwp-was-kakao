package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpResponseBuilder;

import java.util.HashMap;
import java.util.Map;

public class UserCreateController extends Controller {
    private static final String PATH = "/user/create";

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    protected HttpResponse handleGet(HttpRequest httpRequest) {
        Map<String, String> parameters = httpRequest.getParameters();
        addNewUserWithMap(parameters);

        return HttpResponse._200_OK;
    }

    @Override
    protected HttpResponse handlePost(HttpRequest httpRequest) {
        Map<String, String> body = httpRequest.getBodyInMap();
        addNewUserWithMap(body);
        return new HttpResponseBuilder()
                .with302Redirect("/index.html")
                .build();
    }

    private void addNewUserWithMap(Map<String, String> parameters) {
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");
        UserService.addNewUser(userId, password, name, email);
    }
}
