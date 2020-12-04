package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.UserListController;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HandleBarUtils {
    public static byte[] renderTemplate(String location, Object context) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile(location);
            Collection<User> users = UserService.getAllUsers();
            String rendered = template.apply(context);
            return rendered.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("IOException : " + e.getMessage());
        }
    }


}
