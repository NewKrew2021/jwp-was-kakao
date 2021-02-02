package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        User user = new User("gjgj","pwwppw","djdjdj","shcsjd@naver.com");
        DataBase.addUser(new User("choihwan2","pw","최환","choihwan2@naver.com"));
        DataBase.addUser(new User("javajigi", "password", "자바지기", "javajigi@gmail.com"));

        List<User> users = new ArrayList<>(DataBase.findAll());
        Template template = handlebars.compile("user/list");

        Template template = handlebars.compile("user/profile");


        String profilePage = template.apply(users);
        log.debug("userList : {}", profilePage);

        log.debug("users : {}", users);
    }
}
