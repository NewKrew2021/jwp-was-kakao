package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

public class TemplateUtils {
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    public static Handlebars getHandleBars() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (Helper<Integer>) (context, options) -> context + 1);
        return handlebars;
    }
}
