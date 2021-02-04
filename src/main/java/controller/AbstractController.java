package controller;

import request.HttpMethod;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController implements Controller {

    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/create",new CreateUserController());
        controllers.put("/user/login", new LoginController());
    }

    @Override
    public void service(HttpRequest request, HttpResponse response)  throws IOException, URISyntaxException {
        if (request.getMethod().equals(HttpMethod.GET)) {
            doGet(request, response);
        }
        if (request.getMethod().equals(HttpMethod.POST)) {
            doPost(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.badRequest();
    };

    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.badRequest();
    };

}
