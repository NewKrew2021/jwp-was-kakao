package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import model.User;
import model.UsersDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        Handlebars handlebars = TemplateUtils.getHandleBars();

        Template template = handlebars.compile("user/profile");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profilePage = template.apply(user);
        log.debug("ProfilePage : {}", profilePage);
    }

    @Test
    void list() throws Exception {
        Handlebars handlebars = TemplateUtils.getHandleBars();

        Template template = handlebars.compile("user/list");

        UsersDto users = new UsersDto(Collections.singletonList(new User("javajigi", "password", "자바지기", "javajigi@gmail.com")));
        String profilePage = template.apply(users);
        log.debug("ProfilePage : {}", profilePage);
    }
}
