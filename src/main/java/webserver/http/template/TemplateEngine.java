package webserver.http.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public interface TemplateEngine {

    String apply(String path, Object data);

    static TemplateEngine handlebars() {
        return new HandlebarsTemplateEngine();
    }

    class HandlebarsTemplateEngine implements TemplateEngine {

        public static final String DEFAULT_PREFIX = "/templates";
        public static final String DEFAULT_SUFFIX = ".html";

        private TemplateLoader templateLoader;

        public HandlebarsTemplateEngine(){
            this(DEFAULT_PREFIX, DEFAULT_SUFFIX);
        }

        public HandlebarsTemplateEngine(String prefix, String suffix){
            templateLoader = new ClassPathTemplateLoader();
            templateLoader.setPrefix(prefix);
            templateLoader.setSuffix(suffix);
        }

        @Override
        public String apply(String path, Object data) {
            Handlebars handlebars = new Handlebars(templateLoader);
            try {
                Template template = handlebars.compile(path);
                return template.apply(data);
            } catch (IOException e) {
                throw new TemplateEngineException("template 변환중 문제가 발생했습니다", e);
            }
        }
    }
}