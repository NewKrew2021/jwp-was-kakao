package utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileIoUtils {
    private static final String RESOURCES_PATH = "/resources";
    private static final String DOT = ".";
    private static final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(FileIoUtils.class.getClassLoader());

    private FileIoUtils() {
    }

    public static byte[] loadFileFromClasspath(String filePath) {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (resource == null) {
            throw new RuntimeException(filePath + "이(가) 존재하지 않습니다.");
        }

        try {
            Path path = Paths.get(resource.toURI());
            return Files.readAllBytes(path);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<String> getResources(String filePath) {
        Resource[] resources;
        try {
            resources = resolver.getResources(filePath + "/**");
        } catch (IOException e) {
            throw new RuntimeException("파일을 불러올 수 없습니다.");
        }
        return Arrays.stream(resources)
                .map(FileIoUtils::getFile)
                .filter(file -> !file.isDirectory())
                .map(FileIoUtils::getPath)
                .collect(Collectors.toList());
    }

    private static File getFile(Resource resource) {
        try {
            return resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException("파일을 불러올 수 없습니다.");
        }
    }

    private static String getPath(File file) {
        String path = file.getPath();
        int idx = path.lastIndexOf(RESOURCES_PATH);
        return DOT + path.substring(idx + RESOURCES_PATH.length());
    }
}
