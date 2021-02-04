package user.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;

import java.io.IOException;
import java.util.NoSuchElementException;

public class UserView {
    private static final Template LIST_TEMPLATE;
    private static final Template PROFILE_TEMPLATE;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");

        Handlebars handlebars = new Handlebars(loader);
        PROFILE_TEMPLATE = registerNewTemplate(handlebars, "/user/profile");

        handlebars.registerHelper("inc", (value, options) -> (int) value + 1);
        LIST_TEMPLATE = registerNewTemplate(handlebars, "/user/list");
    }

    public static byte[] getUserListHtml() {
        return getTemplateWithContentsApplied(LIST_TEMPLATE, DataBase.findAll());
    }

    public static byte[] getUserProfileHtml(String userId) {
        return getTemplateWithContentsApplied(PROFILE_TEMPLATE, DataBase.findUserById(userId));
    }

    private static Template registerNewTemplate(Handlebars handlebars, String templatePath) {
        try {
            return handlebars.compile(templatePath);
        } catch (IOException e) {
            throw new NoSuchElementException(String.format("Could not find template with path: %s\n%s",
                    templatePath, e.getMessage()));
        }
    }

    private static byte[] getTemplateWithContentsApplied(Template template, Object content) {
        try {
            return template.apply(content)
                    .getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not compile template");
        }
    }
}
