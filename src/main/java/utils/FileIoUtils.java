package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import exceptions.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            logger.debug("FILEPATH:" + filePath);
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }

    public static String loadTemplateWithHandlebars(String filePath, Object context) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile(filePath);
            return template.apply(context);
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }

    public static String getDirectoryPath(String extension) {
        extension = extension.toLowerCase();
        if(extension.equals("html") || extension.equals("ico")) {
            return "templates";
        }
        return "static";
    }
}
