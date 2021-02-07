package controller;

import controller.handler.SecuredHandler;
import model.request.HttpRequest;
import model.response.HttpResponse;
import service.UserService;
import view.View;

import java.io.IOException;
import java.io.OutputStream;

public class UserController extends Controller {
    {
        setBasePath("/user");
        putHandler("/create", "POST", this::handleCreate);
        putHandler("/login", "POST", this::handleLogin);
        putHandler("/list", "GET", new SecuredHandler(this::handleUserList));
        putHandler("/logout", "GET", new SecuredHandler(this::handleLogout));
    }

    private UserService userService = new UserService();

    public void handleCreate(HttpRequest request, HttpResponse response) {
        userService.addUser(request.getParsedBody());
        response.sendRedirect("/index.html");
    }

    public void handleLogin(HttpRequest request, HttpResponse response) {
        if (!userService.login(request.getParsedBody())) {
            response.setCookie("logined=false").sendRedirect("/user/login_failed.html");
            return;
        }
        response.setCookie("logined=true").sendRedirect("/index.html");
    }

    public void handleUserList(HttpRequest request, HttpResponse response) throws IOException {
        byte[] body = View.getUsersView(userService.findAll(), "/user/list");
        response.sendView(body);
    }

    public void handleLogout(HttpRequest request, HttpResponse response) throws IOException {
        response.setCookie("logined=false").sendRedirect("/index.html");
    }
}
