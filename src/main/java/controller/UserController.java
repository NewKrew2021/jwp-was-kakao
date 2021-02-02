package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.handler.SecuredHandler;
import db.DataBase;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Collection;
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

        DataOutputStream dos = new DataOutputStream(out);
        HttpResponse.response302Header(dos, "/index.html");
        HttpResponse.responseWithoutBody(dos);
    }

    public void handleLogin(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("handling Login");
        Map<String, String> bodyParsed = request.getParsedBody();
        log.info("{} {}", bodyParsed.get("userId"), bodyParsed.get("password"));
        User user = DataBase.findUserById(bodyParsed.get("userId"));

        DataOutputStream dos = new DataOutputStream(out);
        if (user == null) {
            HttpResponse.response302Header(dos, "/user/login_failed.html");
            HttpResponse.setCookie(dos, "logined=false; Path=/");
            HttpResponse.responseWithoutBody(dos);
            return;
        }
        HttpResponse.response302Header(dos, "/index.html");
        HttpResponse.setCookie(dos, "logined=true; Path=/");
        HttpResponse.responseWithoutBody(dos);
    }

    public void handleUserList(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("handling User List");
        Collection<User> users = DataBase.findAll();

        DataOutputStream dos = new DataOutputStream(out);
        if (request.getCookie("logined") == null ||
                request.getCookie("logined").equals("false")) {
            Response.response302Header(dos, "/user/login.html");
            Response.responseWithoutBody(dos);
            return;
        }

        log.info("{}", request.getCookie("logined").equals("false"));
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("/user/list");
        String profilePage = template.apply(users);
        log.debug("ProfilePage : {}", profilePage);

        byte[] body = profilePage.getBytes();
        HttpResponse.response200Header(dos, body.length);
        HttpResponse.responseBody(dos, body);
    }

    public void handleLogout(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("handling Logout");

        DataOutputStream dos = new DataOutputStream(out);
        HttpResponse.response302Header(dos, "/index.html");
        HttpResponse.setCookie(dos, "logined=false; Path=/");
        HttpResponse.responseWithoutBody(dos);
    }
}
