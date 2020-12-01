package controller;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import service.UserService;
import webserver.http.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListController extends Controller {
    public static final String PATH = "/user/list.html";

    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @Override
    protected HttpResponse handleGet(HttpRequest httpRequest) {
        Map<String, String> cookie = new HashMap<>();
        httpRequest.getCookiesInMap(cookie);

        if (!isLogined(cookie)) {
            return new HttpResponseBuilder()
                    .with302Redirect("/user/login.html")
                    .build();
        }

        byte[] body = renderBodyWithUserList();
        if (body == null) {
            return HttpResponse._500_INTERNAL_SERVER_ERROR;
        }

        return new HttpResponse(HttpCode._200, body);
    }

    private boolean isLogined(Map<String, String> cookie) {
        if (!cookie.containsKey("logined")) {
            return false;
        }
        return "true".equals(cookie.get("logined"));
    }

    private byte[] renderBodyWithUserList() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile("user/list");
            Collection<User> users = UserService.getAllUsers();
            Map<String, Collection<User>> context = new HashMap<>();
            context.put("context", users);
            String rendered = template.apply(context);
            return rendered.getBytes();
        } catch (IOException e) {
            logger.trace(e.getMessage(), e);
        }

        return null;
    }
}
