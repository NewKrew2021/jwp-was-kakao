package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import exception.FileLoadException;
import exception.FileNotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static final String TEMPLATES_PATH = "./templates";
    public static final String STATIC_PATH = "./static";

    public static final String BASE_PATH = "/";
    public static final String INDEX_PATH = "/index.html";
    public static final String LOGIN_PATH = "/user/login.html";
    public static final String LOGIN_FAIL_PATH = "/user/login_failed.html";

    private static final TemplateLoader loader = new ClassPathTemplateLoader();
    private static final Handlebars handlebars;

    static {
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (IOException | URISyntaxException e) {
            throw new FileLoadException();
        } catch (NullPointerException e) {
            throw new FileNotFoundException();
        }
    }

    public static String loadFileStringFromClasspath(String filePath) {
        return new String(loadFileFromClasspath(filePath), StandardCharsets.UTF_8);
    }

    public static String loadHandleBarFromClasspath(String filePath, Object context) {
        try {
            return new String(handlebars.compile(filePath).apply(context).getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileLoadException();
        } catch (NullPointerException e) {
            throw new FileNotFoundException();
        }
    }
}
