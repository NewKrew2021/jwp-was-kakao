package webserver;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class ResponseManager {

    private static final String TEMPLATE_DIR = "./templates";
    private static final String ASSET_DIR = "./static";

    public static byte[] getContent(String uri) throws IOException, URISyntaxException {

        String fileLocation = getPath(uri);
        return FileIoUtils.loadFileFromClasspath(fileLocation);
    }

    private static String getPath(String uri) {

        return Arrays.asList("/css", "/fonts", "/images", "/js")
                .stream()
                .filter(uri::startsWith)
                .findAny()
                .map(t -> ASSET_DIR.concat(uri))
                .orElse(TEMPLATE_DIR.concat(uri));
    }

}
