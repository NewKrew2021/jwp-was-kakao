package utils;

import java.io.File;
import java.util.Arrays;

public class HttpResponseUtils {

    private static final String LOGINED_TRUE_VALUE = "logined=true; Path=/";
    private static final String LOGINED_FALSE_VALUE = "logined=false; Path=/";

    public static String makeLoginCookie(boolean login) {
        if (login) {
            return LOGINED_TRUE_VALUE;
        }
        return LOGINED_FALSE_VALUE;
    }

    public static String findContentType(String path) {
        return Arrays.stream(ContentTypeUtils.values())
                .filter(contentTypeUtil ->
                        fileExist(contentTypeUtil.getFilePath(), path) && path.contains(contentTypeUtil.getPathContain()))
                .map(ContentTypeUtils::getContentType)
                .findFirst()
                .orElse("");
    }

    public static String findPath(String path) {
        return Arrays.stream(ContentTypeUtils.values())
                .filter(contentTypeUtils -> fileExist(contentTypeUtils.getFilePath(), path))
                .map(contentTypeUtils -> contentTypeUtils.getPathPrefix() + path)
                .findFirst()
                .orElse("");
    }

    private static boolean fileExist(String filePath, String uri) {
        return new File(filePath + uri).exists();
    }

}
