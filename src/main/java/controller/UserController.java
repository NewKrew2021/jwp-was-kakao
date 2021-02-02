package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.HttpRequest;
import model.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private static final Map<MethodPath, Handler> handlers = new HashMap<>();

    private static final String basePath = "/user";

    {
        putHandler("/create", "POST", this::handleCreate);
        putHandler("/login", "POST", this::handleLogin);
        putHandler("/list", "GET", this::handleUserList);
    }

    public static void putHandler(String path, String method, Handler handler) {
        handlers.put(new MethodPath(basePath + path, method), handler);
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
        Response.response302Header(dos, "/index.html");
        Response.responseWithoutBody(dos);
    }

    public void handleLogin(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("handling Login");
        Map<String, String> bodyParsed = request.getParsedBody();
        log.info("{} {}", bodyParsed.get("userId"), bodyParsed.get("password"));
        User user = DataBase.findUserById(bodyParsed.get("userId"));

        DataOutputStream dos = new DataOutputStream(out);
        if (user == null) {
            Response.response302Header(dos, "/user/login_failed.html");
            Response.setCookie(dos, "logined=false; Path=/");
            Response.responseWithoutBody(dos);
            return;
        }
        Response.response302Header(dos, "/index.html");
        Response.setCookie(dos, "logined=true; Path=/");
        Response.responseWithoutBody(dos);
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
        Response.response200Header(dos, body.length);
        Response.responseBody(dos, body);
    }

    @Override
    public boolean hasSameBasePath(String path) {
        return path.startsWith(basePath);
    }

    @Override
    public boolean handle(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        for (Map.Entry<MethodPath, Handler> entry : handlers.entrySet()) {
            log.info("{} {}", request.getPath(), entry.getKey().getPath());
            if (request.getPath().matches(entry.getKey().getPath())) {
                log.info("matched *******************");
                entry.getValue().handle(request, out);
                return true;
            }
        }
        return false;
    }
}
