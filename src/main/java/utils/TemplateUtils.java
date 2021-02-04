package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class TemplateUtils {
    public static byte[] makeListUserTemplate(Collection<User> users) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");
        String result = template.apply(users);
        return IOUtils.decodeData(result).getBytes(StandardCharsets.UTF_8);
    }
}
