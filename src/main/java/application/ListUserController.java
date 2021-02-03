package application;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListUserController extends AbstractController {
    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String url = httpRequest.getUrl();
        if (!isLogin(httpRequest.getHeader("Cookie"))) {
            httpResponse.sendRedirect("login.html");
            return;
        }
        try {
            byte[] body = compileHtmlBody(url);
            httpResponse.forward(url, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isLogin(String header) {
        return header.equals("logined=true");
    }

    private byte[] compileHtmlBody(String url) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(url);
        List<User> users = new ArrayList<>(DataBase.findAll());
        return template.apply(users).getBytes();
    }
}
