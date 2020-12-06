package template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import exception.TemplateEngineException;

import java.io.IOException;

public class HandlebarTemplateEngine {
	public static Handlebars handlebars;

	static {
		TemplateLoader loader = new ClassPathTemplateLoader();
		loader.setPrefix("/templates");
		loader.setSuffix(".html");
		handlebars = new Handlebars(loader);
	}

	public static String apply(String path, Object data) {
		try {
			Template template = handlebars.compile(path);
			return template.apply(data);
		} catch (IOException e) {
			throw new TemplateEngineException("템플릿 지정이 잘못되었습니다.");
		}
	}
}
