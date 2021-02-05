package http.response;

import http.ContentType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static utils.FileIoUtils.loadFileFromClasspath;

public class ResponseFile {

    private ContentType type;
    private byte[] fileContent;

    public ResponseFile(String urlPath) throws IOException, URISyntaxException {
        if (urlPath.contains(".html")) {
            type = ContentType.HTML;
            fileContent = loadFileFromClasspath("./templates" + urlPath);
        }
        if (urlPath.contains(".ico")) {
            type = ContentType.ICO;
            fileContent = loadFileFromClasspath("./templates" + urlPath);
        }
        if (urlPath.matches("(.*)(.js|.css|.eot|.svg|.ttf|.woff|.woff2|.png)")) {
            List<String> split = Arrays.asList(urlPath.split("\\."));
            String extension = split.get(split.size() - 1);
            type = ContentType.valueOf(extension.toUpperCase());
            fileContent = loadFileFromClasspath("./static" + urlPath);
        }
    }

    public ContentType getContentType() {
        return type;
    }

    public byte[] getFileContent() {
        return fileContent;
    }
}
