package controller;

import controller.handler.SecuredHandler;
import db.DataBase;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import service.UserService;
import view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class UserController extends Controller {
    {
        setBasePath("/user");
        putHandler("/create", "POST", this::handleCreate);
        putHandler("/login", "POST", this::handleLogin);
        putHandler("/list", "GET", new SecuredHandler(this::handleUserList));
        putHandler("/logout", "GET", new SecuredHandler(this::handleLogout));
    }

    private UserService userService = new UserService();

    public void handleCreate(HttpRequest request, OutputStream out) {
        userService.addUser(request.getParsedBody());
        HttpResponse.of(out).sendRedirect("/index.html");
    }

    public void handleLogin(HttpRequest request, OutputStream out) {
        if (!userService.login(request.getParsedBody())) {
            HttpResponse.of(out).setCookie("logined=false").sendRedirect("/user/login_failed.html");
            return;
        }
        HttpResponse.of(out).setCookie("logined=true").sendRedirect("/index.html");
    }

    public void handleUserList(HttpRequest request, OutputStream out) throws IOException {
        byte[] body = View.getUsersView(userService.findAll(), "/user/list");
        HttpResponse.of(out).sendView(body);
    }

    public void handleLogout(HttpRequest request, OutputStream out) throws IOException {
        HttpResponse.of(out).setCookie("logined=false").sendRedirect("/index.html");
    }
}
