package utils;

import http.ContentType;
import http.response.Response;
import http.response.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static Response loadFileFromUrlPath(String urlPath) throws IOException, URISyntaxException {
        if (urlPath.contains(".html")) {
            return Response.ofDefaultFile(
                    new ResponseBody(loadFileFromClasspath("./templates" + urlPath)),
                    ContentType.HTML
            );
        }
        if (urlPath.contains(".ico")) {
            return Response.ofDefaultFile(
                    new ResponseBody(loadFileFromClasspath("./templates" + urlPath)),
                    ContentType.ICO);
        }
        if (urlPath.matches("(.*)(.js|.css|.eot|.svg|.ttf|.woff|.woff2|.png)")) {
            List<String> split = Arrays.asList(urlPath.split("\\."));
            String extension = split.get(split.size() - 1);
            return Response.ofDefaultFile(
                    new ResponseBody(loadFileFromClasspath("./static" + urlPath)),
                    ContentType.valueOf(extension.toUpperCase()));
        }
        return Response.ofDefaultFile(new ResponseBody("No Page!"),
                ContentType.HTML);
    }

    public static boolean pathIsFile(String urlPath) {
        return urlPath.matches("(.*)(.html|.js|.css|.eot|.svg|.ttf|.woff|.woff2|.png|.ico)");
    }
}
