package webserver;

import utils.FileIoUtils;

public class ResponseHandler {

    private static final String DEFAULT_VIEW = "Hello World";

    public static byte[] getBody(String urlPath) {
        try {
            return FileIoUtils.loadFileFromClasspath(getViewPath(urlPath));
        } catch (Exception e) {
            return DEFAULT_VIEW.getBytes();
        }
    }

    public static String getViewPath(String path) {
        if (path.startsWith("/css")
                || path.startsWith("/fonts")
                || path.startsWith("/images")
                || path.startsWith("/js")) {
            return "./static" + path;
        }
        return "./templates" + path + "";
    }
}
