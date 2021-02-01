package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("파일을 읽어올 수 없습니다.");
        }
    }

    public static byte[] loadHandleBarFromClasspath(String filePath, Object context) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile(filePath);
            return template.apply(context).getBytes();
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽어올 수 없거나 컨텍스트를 적용할 수 없습니다.");
        }
    }
}
