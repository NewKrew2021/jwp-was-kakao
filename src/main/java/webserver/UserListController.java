package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;

import java.io.IOException;
import java.net.URLDecoder;

public class UserListController extends AbstractController {

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getCookie("logined") == null || httpRequest.getCookie("logined").equals("false")) {
            httpResponse.sendRedirect("/user/login.html");
            return;
        }

        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile("user/list");
            String profilePage = template.apply(DataBase.findAll());
            httpResponse.handleUserList(URLDecoder.decode(profilePage, "UTF-8"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
