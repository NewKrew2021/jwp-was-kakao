package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.Constant;
import model.User;

import java.io.IOException;
import java.util.Collection;

public class View {
    public static byte[] getUsersView(Collection<User> users, String url) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(Constant.TEMPLATES_PATH_VIEW);
        loader.setSuffix(Constant.HTML);
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(url);
        String profilePage = template.apply(users);

        return profilePage.getBytes();
    }
}
