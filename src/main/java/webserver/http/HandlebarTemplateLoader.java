package webserver.http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class HandlebarTemplateLoader {


    private final Handlebars handlebars;

    private final Template template;

    public HandlebarTemplateLoader(String viewFile) throws IOException {

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);

        template = handlebars.compile(viewFile);
    }

    public <T> String applyTemplateValue(T object) throws IOException {
        return template.apply(object);
    }
}
