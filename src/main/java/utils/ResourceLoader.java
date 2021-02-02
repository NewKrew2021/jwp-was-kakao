package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class ResourceLoader {
    private static final String STATIC_PATH = "./static";
    private static final String TEMPLATES_PATH = "./templates";
    private static final List<String> TEMPLATES_EXT = Arrays.asList(".html", ".ico");
    private static final TemplateLoader loader = new ClassPathTemplateLoader();

    static {
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
    }

    private ResourceLoader() {
    }

    public static byte[] getBytes(String filePath) {
        String root = TEMPLATES_EXT.contains(getExt(filePath)) ? TEMPLATES_PATH : STATIC_PATH;
        return FileIoUtils.loadFileFromClasspath(root + filePath);
    }

    private static String getExt(String filePath) {
        int idx = filePath.lastIndexOf('.');
        if (idx < 0) {
            throw new IllegalArgumentException("파일 형식이 아닙니다.");
        }
        return filePath.substring(idx);
    }

    public static byte[] getDynamicPage(String filePath) {
        try {
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile(filePath);
            List<User> users = UserService.findAllUsers();
            String page = template.apply(users);
            return page.getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("파일을 불러올 수 없습니다.");
        }
    }
}
