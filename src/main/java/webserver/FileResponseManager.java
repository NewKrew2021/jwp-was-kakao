package webserver;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileResponseManager {

    private static final String BASE_DIR = "./templates";

    public static byte[] getFileContent(String path) throws IOException, URISyntaxException {

        String fileLocation = BASE_DIR + path;
        return FileIoUtils.loadFileFromClasspath(fileLocation);

    }

}
