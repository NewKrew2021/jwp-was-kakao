package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class HandlebarsUtils {

    private static final TemplateLoader loader;
    private static final Handlebars handlebars;

    static {
        loader = new ClassPathTemplateLoader("/templates", ".html");
        handlebars = new Handlebars(loader);
    }

    public static Template getTemplate(String location) throws IOException {
        return handlebars.compile(location);
    }
}
