package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import exception.FileCannotLoadedException;
import exception.FileNotExistException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
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
            throw new FileCannotLoadedException();
        } catch (NullPointerException e) {
            throw new FileNotExistException();
        }
    }

    public static String loadHandleBarFromClasspath(String filePath, Object context) {
        try {
            return new String(handlebars.compile(filePath).apply(context).getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileNotExistException();
        }
    }
}
