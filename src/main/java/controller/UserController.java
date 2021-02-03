package controller;

import controller.handler.SecuredHandler;
import db.DataBase;
import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class UserController extends Controller {
    {
        setBasePath("/user");
        putHandler("/create", HttpMethod.POST, this::handleCreate);
        putHandler("/login", HttpMethod.POST, this::handleLogin);
        putHandler("/list", HttpMethod.GET, new SecuredHandler(this::handleUserList));
        putHandler("/logout", HttpMethod.GET, this::handleLogout);
    }

    public HttpResponse handleCreate(HttpRequest request, OutputStream out) {
        Map<String, String> bodyParsed = request.getParsedBody();
        User user = new User(
                bodyParsed.get("userId"),
                bodyParsed.get("password"),
                bodyParsed.get("name"),
                bodyParsed.get("email")
        );
        DataBase.addUser(user);

        return HttpResponse.of(out).redirect("/index.html");
    }

    public HttpResponse handleLogin(HttpRequest request, OutputStream out) {
        Map<String, String> bodyParsed = request.getParsedBody();
        User user = DataBase.findUserById(bodyParsed.get("userId"));

        if (user == null) {
            return HttpResponse.of(out).setCookie("logined=false").redirect("/user/login_failed.html");
        }
        return HttpResponse.of(out).setCookie("logined=true").redirect("/index.html");
    }

    public HttpResponse handleUserList(HttpRequest request, OutputStream out) throws IOException {
        byte[] body = View.getUsersView(DataBase.findAll(), "/user/list");
        return HttpResponse.of(out).view(body);
    }

    public HttpResponse handleLogout(HttpRequest request, OutputStream out) {
        return HttpResponse.of(out).setCookie("logined=false").redirect("/index.html");
    }
}
