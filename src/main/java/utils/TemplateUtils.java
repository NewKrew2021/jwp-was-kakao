package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.cache.ConcurrentMapTemplateCache;
import com.github.jknack.handlebars.helper.DefaultHelperRegistry;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class TemplateUtils {
    static Handlebars handlebars;
    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader)
                .with(new DefaultHelperRegistry().registerHelper("inc", (Helper<Integer>) (context, options) -> context + 1))
                .with(new ConcurrentMapTemplateCache());

    }

    public static Template getTemplate(String name) throws IOException {
        return handlebars.compile(name);
    }

    public static Template getTemplateInline(String input) throws IOException {
        return handlebars.compileInline(input);
    }
}
