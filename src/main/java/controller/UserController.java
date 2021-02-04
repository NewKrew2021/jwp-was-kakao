package controller;

import controller.handler.SecuredHandler;
import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
import service.UserService;

import java.io.IOException;

public class UserController extends Controller {
    {
        setBasePath("/user");
        putHandler("/create", HttpMethod.POST, this::handleCreate);
        putHandler("/login", HttpMethod.POST, this::handleLogin);
        putHandler("/list", HttpMethod.GET, new SecuredHandler(this::handleUserList));
        putHandler("/logout", HttpMethod.GET, this::handleLogout);
    }

    public HttpResponse handleCreate(HttpRequest request) {
        return UserService.createUser(request);
    }

    public HttpResponse handleLogin(HttpRequest request) {
        return UserService.userLogin(request);
    }

    public HttpResponse handleUserList(HttpRequest request) throws IOException {
        return UserService.getUserList(request);
    }

    public HttpResponse handleLogout(HttpRequest request) {
        return UserService.userLogout(request);
    }
}
