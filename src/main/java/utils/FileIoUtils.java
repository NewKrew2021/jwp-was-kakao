package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

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
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (resource == null) {
            logger.error(filePath + "이(가) 존재하지 않습니다.");
            return new byte[0];
        }

        Path path = Paths.get(resource.toURI());
        return Files.readAllBytes(path);
    }

    public static List<String> loadFileList(String filePath) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(FileIoUtils.class.getClassLoader());
        Resource[] resources = resolver.getResources(filePath + "/**");

        return Arrays.stream(resources)
                .map(resource -> getPath(resource, filePath))
                .filter(FileIoUtils::isFile)
                .collect(Collectors.toList());
    }

    private static String getPath(Resource resource, String filePath) {
        int dotIdx = filePath.indexOf("./");
        filePath = filePath.substring(dotIdx + 1);

        try {
            String fullPath = resource.getFile().getPath();
            int beginIdx = fullPath.lastIndexOf(filePath);
            return fullPath.substring(beginIdx);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private static boolean isFile(String name) {
        return name != null && name.contains(".");
    }
}
