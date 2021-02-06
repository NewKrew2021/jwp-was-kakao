package framework.common;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TemplateEngine {

    private static final Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    public static String apply(String path, Map<String, List> parameters) throws IOException {
        return handlebars.compile(path).apply(parameters);
    }
}
