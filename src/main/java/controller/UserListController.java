package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import exception.FileIOException;
import exception.HttpResponseOutputException;

import java.io.IOException;

public class UserListController extends AbstractController {
    private static final String USER_LOGIN_HTML_URL = "/user/login.html";
    private static final String USER_LIST_URL = "/user/list";

    private static final String LOGIN_COOKIE_KEY = "logined";
    private static final String LOGIN_COOKIE_VALUE_FALSE = "false";
    private static final String USER_LIST_PREFIX = "/templates";
    private static final String USER_LIST_SUFFIX = ".html";

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws HttpResponseOutputException, FileIOException {
        if (httpRequest.getCookie(LOGIN_COOKIE_KEY) == null
                || httpRequest.getCookie(LOGIN_COOKIE_KEY).equals(LOGIN_COOKIE_VALUE_FALSE)) {
            httpResponse.redirect(USER_LOGIN_HTML_URL).send();
            return;
        }

        httpResponse.ok(USER_LIST_URL)
                .body(makeProfilePage().getBytes())
                .send();
    }

    private String makeProfilePage() throws FileIOException {
        String profilePage = null;
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix(USER_LIST_PREFIX);
            loader.setSuffix(USER_LIST_SUFFIX);
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile("user/list");
            profilePage = template.apply(DataBase.findAll());
        } catch (IOException e) {
            throw new FileIOException(USER_LIST_URL);
        }

        return profilePage;
    }

    @Override
    public boolean isSupport(String path) {
        return path.equals(USER_LIST_URL);
    }

}
