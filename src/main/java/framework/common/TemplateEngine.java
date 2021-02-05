package framework.common;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TemplateEngine {

    public static String apply(String path, Map<String, List> parameters) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");

        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(path);

        return template.apply(parameters);
    }
}
