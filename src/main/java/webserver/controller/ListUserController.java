package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.HttpHeader;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUserController extends AbstractController {
    private TemplateLoader loader;
    private Handlebars handlebars;
    private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);


    public ListUserController() {
        loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        if (!isLogin(httpRequest.getHeader(HttpHeader.COOKIE))) {
            httpResponse.sendRedirect("/user/login.html");
            return;
        }
        try {
            httpResponse.forwardBody(getUserListPage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getUserListPage() throws IOException {
        Template template = handlebars.compile("user/list");

        Map<String, List<User>> users = new HashMap();
        users.put("users", new ArrayList<>(DataBase.findAll()));
        return template.apply(users);
    }

    private boolean isLogin(String loginCookie) {
        return loginCookie.contains("logined=true");
    }
}
