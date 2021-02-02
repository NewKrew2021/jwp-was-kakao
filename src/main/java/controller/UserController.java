package controller;

import controller.handler.SecuredHandler;
import db.DataBase;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Map;

public class UserController extends Controller {
    {
        setBasePath("/user");
        putHandler("/create", "POST", this::handleCreate);
        putHandler("/login", "POST", this::handleLogin);
        putHandler("/list", "GET", new SecuredHandler(this::handleUserList));
        putHandler("/logout", "GET", new SecuredHandler(this::handleLogout));
    }

    public void handleCreate(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        Map<String, String> bodyParsed = request.getParsedBody();
        User user = new User(
                bodyParsed.get("userId"),
                bodyParsed.get("password"),
                bodyParsed.get("name"),
                bodyParsed.get("email")
        );
        DataBase.addUser(user);

        HttpResponse.of(out).sendRedirect("/index.html");
    }

    public void handleLogin(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        Map<String, String> bodyParsed = request.getParsedBody();
        User user = DataBase.findUserById(bodyParsed.get("userId"));

        if (user == null) {
            HttpResponse.of(out).setCookie("logined=false").sendRedirect("/user/login_failed.html");
            return;
        }
        HttpResponse.of(out).setCookie("logined=true").sendRedirect("/index.html");
    }

    public void handleUserList(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        byte[] body = View.getUsersView(DataBase.findAll(), "/user/list");
        HttpResponse.of(out).sendView(body);
    }

    public void handleLogout(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        HttpResponse.of(out).setCookie("logined=false").sendRedirect("/index.html");
    }
}
