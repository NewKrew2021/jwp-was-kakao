package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static byte[] loadFileFromUrlPath(String urlPath) throws IOException, URISyntaxException {
        if (urlPath.contains(".html")) {
            return loadFileFromClasspath("./templates" + urlPath);
        }
        if (urlPath.matches("(.*)(.js|.css|.eot|.svg|.ttf|.woff|.woff2|.png)")) {
            return loadFileFromClasspath("./static" + urlPath);
        }
        return "No Page!".getBytes();
    }

    public static boolean pathIsFile(String urlPath){
        return urlPath.matches("(.*)(.html|.js|.css|.eot|.svg|.ttf|.woff|.woff2|.png)");
    }
}
