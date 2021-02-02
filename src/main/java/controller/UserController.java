package controller;

import controller.handler.SecuredHandler;
import db.DataBase;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Map;

public class UserController extends Controller {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    {
        setBasePath("/user");
        putHandler("/create", "POST", this::handleCreate);
        putHandler("/login", "POST", this::handleLogin);
        putHandler("/list", "GET", new SecuredHandler(this::handleUserList));
        putHandler("/list.html", "GET", new SecuredHandler(this::handleUserList));
        putHandler("/logout", "GET", new SecuredHandler(this::handleLogout));
    }

    public void handleCreate(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("handling Creation");
        Map<String, String> bodyParsed = request.getParsedBody();
        User user = new User(
                bodyParsed.get("userId"),
                bodyParsed.get("password"),
                bodyParsed.get("name"),
                bodyParsed.get("email")
        );
        DataBase.addUser(user);

        HttpResponse.of(out).setStatus(302).setLocation("/index.html").ok();
    }

    public void handleLogin(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("handling Login");
        Map<String, String> bodyParsed = request.getParsedBody();
        log.info("{} {}", bodyParsed.get("userId"), bodyParsed.get("password"));
        User user = DataBase.findUserById(bodyParsed.get("userId"));

        if (user == null) {
            HttpResponse.of(out).setStatus(302).setLocation("/user/login_failed.html").setCookie("logined=false; Path=/").ok();
            return;
        }
        HttpResponse.of(out).setStatus(302).setLocation("/index.html").setCookie("logined=true; Path=/").ok();
    }

    public void handleUserList(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("handling User List");

        byte[] body = View.getUsersView(DataBase.findAll(), "/user/list");
        HttpResponse.of(out).setStatus(200).sendView(body);
    }

    public void handleLogout(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("handling Logout");
        HttpResponse.of(out).setStatus(302).setLocation("/index.html").setCookie("logined=false; Path=/").ok();
    }
}
