package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TemplateUtils {

    private TemplateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static byte[] makeTemplate(String path, Object data) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(path);
        return template.apply(data).getBytes(StandardCharsets.UTF_8);
    }
}
