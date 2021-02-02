package utils;

import domain.Response;
import domain.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static Response loadFileFromUrlPath(String urlPath) throws IOException, URISyntaxException {
        if (urlPath.contains(".html")) {
            return Response.ofDefaultFile(
                    new ResponseBody(
                            loadFileFromClasspath("./templates" + urlPath)));
        }
        if (urlPath.matches("(.*)(.js|.css|.eot|.svg|.ttf|.woff|.woff2|.png)")) {
            return Response.ofDefaultFile(
                    new ResponseBody(
                            loadFileFromClasspath("./static" + urlPath)));
        }
        return Response.ofDefaultFile(
                new ResponseBody("No Page!"));
    }

    public static boolean pathIsFile(String urlPath) {
        return urlPath.matches("(.*)(.html|.js|.css|.eot|.svg|.ttf|.woff|.woff2|.png)");
    }
}
