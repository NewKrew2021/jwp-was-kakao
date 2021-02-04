package user.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import user.service.UserService;

import java.io.IOException;

public class UserView {
    private static final TemplateLoader loader;

    static {
        loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
    }

    public static byte[] getUserListHtml() {
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (value, options) -> (int) value + 1);

        try {
            Template template = handlebars.compile("user/list");
            String userList = template.apply(UserService.findAll());

            return userList.getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not compile list.html template");
        }
    }

    public static byte[] getUserProfileHtml(String userId) {
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile("user/profile");
            String userList = template.apply(UserService.findById(userId));

            return userList.getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not compile list.html template");
        }
    }
}
