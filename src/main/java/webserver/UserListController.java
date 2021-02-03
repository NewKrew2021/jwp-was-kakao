package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;

import java.io.IOException;

public class UserListController extends AbstractController {
    private final String LOGIN_COOKIE_KEY = "logined";
    private final String LOGIN_COOKIE_VALUE_FALSE = "false";
    private final String USER_LOGIN_URL = "/user/login.html";
    private final String USER_LIST_URL = "/user/list";
    private final String USER_LIST_PREFIX = "/template";
    private final String USER_LIST_SUFFIX = ".html";

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getCookie(LOGIN_COOKIE_KEY) == null
                || httpRequest.getCookie(LOGIN_COOKIE_KEY).equals(LOGIN_COOKIE_VALUE_FALSE)) {
            httpResponse.sendRedirect(USER_LOGIN_URL);
            return;
        }

        httpResponse.forwardBody(USER_LIST_URL, makeProfilePage());
    }

    private String makeProfilePage() {
        String profilePage = null;
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix(USER_LIST_PREFIX);
            loader.setSuffix(USER_LIST_SUFFIX);
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile("user/list");
            profilePage = template.apply(DataBase.findAll());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return profilePage;
    }

}
