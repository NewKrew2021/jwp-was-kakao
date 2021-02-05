package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.Resource;
import model.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ResourceLoader {
    private static final String STATIC_PATH = "./static";
    private static final String TEMPLATES_PATH = "./templates";
    private static final List<String> TEMPLATES_EXT = Arrays.asList("html", "ico");
    private static final TemplateLoader loader = new ClassPathTemplateLoader();

    static {
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
    }

    private ResourceLoader() {
    }

    public static Resource getResource(String path) {
        try {
            String ext = getExtension(path);
            String root = TEMPLATES_EXT.contains(ext) ? TEMPLATES_PATH : STATIC_PATH;
            byte[] bytes = FileIoUtils.loadFileFromClasspath(root + path);
            return Resource.of(bytes, ext);
        } catch (Exception e) {
            String message = path + "을(를) 불러올 수 없습니다. (Caused By " + e.getMessage() + ")";
            throw new IllegalStateException(message);
        }
    }

    private static String getExtension(String path) {
        int idx = path.lastIndexOf('.');
        if (idx == -1 || idx == path.length() - 1) {
            throw new IllegalArgumentException(path + "는 파일 형식이 아닙니다.");
        }
        return path.substring(idx + 1);
    }

    public static Resource getDynamicPage(String path) {
        try {
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile(path);
            List<User> users = UserService.findAllUsers();
            String page = template.apply(users);
            return Resource.of(page.getBytes(), "html");
        } catch (IOException e) {
            throw new IllegalStateException(path + "을(를) 불러올 수 없습니다.");
        }
    }
}
