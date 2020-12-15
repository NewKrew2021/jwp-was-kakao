package utils.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import model.Model;
import model.user.User;
import model.user.UsersDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static resource.ResourceResolver.TEMPLATE_PATH_PREFIX;

public class TemplateUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(TemplateUtilsTest.class);

    @Test
    void name() throws Exception {
        Handlebars handlebars = TemplateUtils.getHandleBars();

        Template template = handlebars.compile(TEMPLATE_PATH_PREFIX + "/user/profile.html");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profilePage = template.apply(user);
        log.debug("ProfilePage : {}", profilePage);
    }

    @Test
    void list() throws Exception {
        Handlebars handlebars = TemplateUtils.getHandleBars();

        Template template = handlebars.compile(TEMPLATE_PATH_PREFIX + "/user/list.html");

        UsersDto users = new UsersDto(Collections.singletonList(new User("javajigi", "password", "자바지기", "javajigi@gmail.com")));
        Model model = Model.empty();
        model.put("usersDto", users);
        String listPage = template.apply(model.getData());
        log.debug("ListPage : {}", listPage);

        assertThat(listPage).contains("<th scope=\"row\">1</th>");
        assertThat(listPage).contains("javajigi");
        assertThat(listPage).contains("자바지기");
        assertThat(listPage).contains("javajigi@gmail.com");
    }
}
