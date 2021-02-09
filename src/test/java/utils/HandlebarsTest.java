package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    @DisplayName("유저 리스트 검증")
    void check_user_list() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        DataBase.addUser(new User("choihwan2","pw","최환","choihwan2@naver.com"));
        DataBase.addUser(new User("javajigi", "password", "자바지기", "javajigi@gmail.com"));

        List<User> users = new ArrayList<>(DataBase.findAll());
        Template template = handlebars.compile("user/list");
        String userListPage = template.apply(users);
        log.debug("userList : {}", userListPage);
        log.debug("users : {}", users);

        assertThat(userListPage).contains("<tr>\n" +
                "                    <th scope=\"row\">0</th>\n" +
                "                    <td>choihwan2</td>\n" +
                "                    <td>최환</td>\n" +
                "                    <td>choihwan2@naver.com</td>\n");
        assertThat(userListPage).contains("<tr>\n" +
                "                    <th scope=\"row\">1</th>\n" +
                "                    <td>javajigi</td>\n" +
                "                    <td>자바지기</td>\n" +
                "                    <td>javajigi@gmail.com</td>\n");
    }
}
