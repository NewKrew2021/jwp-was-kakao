package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

public class TemplateUtils {
    private static TemplateUtils templateUtils;
    private Handlebars handlebars;

    private TemplateUtils() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    public static TemplateUtils getInstance() {
        if (templateUtils == null) {
            templateUtils = new TemplateUtils();
        }
        return templateUtils;
    }

    public byte[] buildPage(String path, Map<String, Object> params) throws IOException {
        Template template = handlebars.compile(path);
        return template.apply(params).getBytes();
    }
}
