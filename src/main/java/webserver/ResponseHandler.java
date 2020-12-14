package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import utils.FileIoUtils;
import utils.MessageUtils;

import java.io.IOException;

public class ResponseHandler {

    private static final String DEFAULT_VIEW = "Hello World";

    private ResponseHandler() {
        throw new IllegalStateException(MessageUtils.UTILITY_CLASS);
    }

    public static byte[] getBody(String urlPath) {
        try {
            return FileIoUtils.loadFileFromClasspath(getViewPath(urlPath));
        } catch (Exception e) {
            return DEFAULT_VIEW.getBytes();
        }
    }

    public static boolean isStaticFile(String path) {
        return path.contains(".html");
    }

    public static String getViewPath(String path) {
        if (path.startsWith("/css")
                || path.startsWith("/fonts")
                || path.startsWith("/images")
                || path.startsWith("/js")) {
            return "./static" + path;
        }
        return "./templates" + path + "";
    }

    public static String createTemplatesView(String location, Object data) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = null;
        try {
            template = handlebars.compile(location);
            return template.apply(data);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
