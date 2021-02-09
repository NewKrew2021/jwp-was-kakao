package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import exception.HttpException;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class HandlebarsUtils {
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    private static Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);
        handlebars = new Handlebars(loader);
    }

    public static String render(String path, Object data) throws HttpException {
        try {
            Template template = handlebars.compile(path);
            return template.apply(data);
        } catch (IOException e) {
            throw new HttpException(HttpStatus.NOT_FOUND);
        }
    }

}
