package service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwpService {
    public static String getProfilePage() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");

        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile("user/list");

        Map<String, List<User>> users = new HashMap();
        users.put("users", new ArrayList<>(DataBase.findAll()));
        return template.apply(users);
    }

    public static boolean isLogin(String loginCookie) {
        if (loginCookie == null) {
            return false;
        }
        return loginCookie.contains("logined=true");
    }
}
