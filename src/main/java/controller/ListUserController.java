package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.PagePath;
import model.User;
import utils.FileIoUtils;
import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!isPossibleAccessUserList(httpRequest.getPath())) {
            httpResponse.sendRedirect(USER_LOGIN_URL);
            return;
        }

        try {
            byte[] body = makeUserListBody(httpRequest.getPath().getPagePath());
            httpResponse.response200Header(body.length);
            httpResponse.responseBody(body);
        } catch (IOException e) {
            e.printStackTrace();
            httpResponse.response404();
        }

    }

    private boolean isPossibleAccessUserList(PagePath pagePath) {
        try {
            return login.isLogin() && FileIoUtils.isExistFile("./templates" + pagePath.getPagePath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    private byte[] makeUserListBody(String path) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(path);
        List<User> users = new ArrayList<>(DataBase.findAll());
        return template.apply(users).getBytes(StandardCharsets.UTF_8);
    }

}
