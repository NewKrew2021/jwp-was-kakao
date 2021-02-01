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

import java.io.IOException;
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

        Template template = handlebars.compile("user/profile");

        User user = new User("ocean.world", "패스워드1", "닉네임1", "ocean@eamil.com");
        User user2 = new User("eli.nabro", "패스워드2", "닉네임2", "eli@eamil.com");
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        String profilePage = template.apply(user);
        log.debug("ProfilePage : {}", profilePage);
    }

    @Test
    void handlerTest() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");

        User user = new User("ocean.world", "패스워드1", "닉네임1", "ocean@eamil.com");
        User user2 = new User("eli.nabro", "패스워드2", "닉네임2", "eli@eamil.com");
        User user3 = new User("pobi", "패스워드3", "닉네임3", "Pobi@eamil.com");

        DataBase.addUser(user);
        DataBase.addUser(user2);
        DataBase.addUser(user3);

        List<User> users = new ArrayList<>(DataBase.findAll());

        String profilePage = template.apply(users);
        log.debug("ProfilePage : {}", profilePage);
    }


}
