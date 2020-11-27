package webserver.http.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.io.IOException;

public interface TemplateEngine {

    String apply(String template, Object data);

    static TemplateEngine handlebars(){
        return new HandlebarsTemplateEngine();
    }

    class HandlebarsTemplateEngine implements TemplateEngine {
        @Override
        public String apply(String templateContent, Object data) {
            Handlebars handlebars = new Handlebars();
            try {
                Template template = handlebars.compileInline(templateContent);
                return template.apply(data);
            } catch (IOException e) {
                throw new TemplateEngineException("template 변환중 문제가 발생했습니다", e);
            }
        }
   }
}