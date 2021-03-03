package application;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListUserController extends AbstractController {

    public static final String COOKIE = "Cookie";
    public static final String LOGIN_HTML = "login.html";
    public static final String LOGINED_TRUE = "logined=true";
    public static final String TEMPLATES = "/templates";
    public static final String HTML = ".html";

    private final Logger logger = LoggerFactory.getLogger(ListUserController.class.getName());

    boolean isLogin(String header) {
        return header.equals(LOGINED_TRUE);
    }

    private byte[] compileHtmlBody(String url) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES);
        loader.setSuffix(HTML);
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(url);
        List<User> users = new ArrayList<>(DataBase.findAll());
        return template.apply(users).getBytes();
    }

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String url = httpRequest.getUrl();
        if (!isLogin(httpRequest.getHeader(COOKIE))) {
            httpResponse.sendRedirect(LOGIN_HTML);
            return;
        }
        try {
            byte[] body = compileHtmlBody(url);
            httpResponse.forward(url, body);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}
