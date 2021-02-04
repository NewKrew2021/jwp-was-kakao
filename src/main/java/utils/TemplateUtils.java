package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.util.Map;
import java.io.IOException;

public class TemplateUtils {
    private static final Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    public static byte[] getTemplatePage(String path, Map<String,Object> parameter) throws IOException {
        Template template = handlebars.compile(path);
        return template.apply(parameter).getBytes();
    }
}
