package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemplateUtils {
    private static final String TEMPLATES_PREFIX = "/templates";

    public static String makeTemplate(String path) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES_PREFIX);
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(path);

        return template.apply(DataBase.findAll());
    }
}
