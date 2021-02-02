package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static String loadFileFromClasspath(String filePath) {
        return loadFileFromClasspath(filePath, StandardCharsets.UTF_8);
    }

    public static String loadFileFromClasspath(String filePath, Charset charset) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return new String(Files.readAllBytes(path), charset);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("파일을 읽어올 수 없습니다.");
        }
    }

    public static String loadHandleBarFromClasspath(String filePath, Object context) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile(filePath);
            return new String(template.apply(context).getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽어올 수 없거나 컨텍스트를 적용할 수 없습니다.");
        }
    }
}
