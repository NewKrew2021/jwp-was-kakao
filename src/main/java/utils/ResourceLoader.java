package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import exception.InvalidFileException;
import model.User;
import service.UserService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class ResourceLoader {
    private static final String WRONG_FILE_EXT_MESSAGE = "파일 확장자가 없습니다.";
    private static final String UNABLE_GET_PAGE_MESSAGE = "페이지를 생성할 수 없습니다.";

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
        String root = TEMPLATES_EXT.contains(getExtension(filePath)) ? TEMPLATES_PATH : STATIC_PATH;
        return FileIoUtils.loadFileFromClasspath(root + filePath);
    }

    private static String getExtension(String filePath) {
        int idx = filePath.lastIndexOf('.');
        if (idx < 0) {
            throw new InvalidFileException(WRONG_FILE_EXT_MESSAGE);
        }
        return filePath.substring(idx);
    }

    public static byte[] getDynamicPageWithUser(String filePath) {
        try {
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile(filePath);
            List<User> users = UserService.findAllUsers();
            String page = template.apply(users);
            return page.getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new InvalidFileException(UNABLE_GET_PAGE_MESSAGE);
        }
    }
}
