package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profilePage = template.apply(user);
        log.debug("ProfilePage : {}", profilePage);
    }

    @DisplayName("리스트오 인덱스를 처리할 수 있다.")
    @Test
    void listAndIndex() throws IOException {
        //@formatter:off
        String templateString = "{{#this}}\n" +
                "              <tr>\n" +
                "                  <th scope=\"row\">{{inc @index}}</th> <td>{{userId}}</td> <td>{{name}}</td> <td>{{email}}</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
                "              </tr>\n" +
                "{{/this}}";
        //@formatter:on

        Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader());
        handlebars.registerHelper("inc", (Helper<Integer>) (context, options) -> context + 1);
        Template template = handlebars.compileInline(templateString);
        String page = template.apply(DataBase.findAll());
        System.out.println("page = " + page);
        //@formatter:off
        assertThat(page).isEqualTo("\n" +
                "              <tr>\n" +
                "                  <th scope=\"row\">1</th> <td>green</td> <td>초록이</td> <td>green@color.com</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
                "              </tr>\n" +
                "\n" +
                "              <tr>\n" +
                "                  <th scope=\"row\">2</th> <td>blue</td> <td>파랑이</td> <td>blue@color.com</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
                "              </tr>\n" +
                "\n" +
                "              <tr>\n" +
                "                  <th scope=\"row\">3</th> <td>white</td> <td>하양이</td> <td>white@color.com</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
                "              </tr>\n" +
                "\n" +
                "              <tr>\n" +
                "                  <th scope=\"row\">4</th> <td>yellow</td> <td>노랑이</td> <td>yellow@color.com</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
                "              </tr>\n" +
                "\n" +
                "              <tr>\n" +
                "                  <th scope=\"row\">5</th> <td>black</td> <td>까망이</td> <td>black@color.com</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
                "              </tr>\n");
        //@formatter:on

    }
}
