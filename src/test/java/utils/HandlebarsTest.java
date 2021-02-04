package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");

        User user1 = new User("yuni", "passyuni", "윤대승", "dsyun96@kakao.com");
        User user2 = new User("corby", "passcorby", "김범준", "skyblue300a@kakao.com");
        String userList = template.apply(Arrays.asList(user1, user2));
        log.debug("ProfilePage : {}", userList);

        assertThat(userList).contains("yuni");
        assertThat(userList).contains("윤대승");
        assertThat(userList).contains("dsyun96");
        assertThat(userList).contains("corby");
        assertThat(userList).contains("skyblue");
    }
}
