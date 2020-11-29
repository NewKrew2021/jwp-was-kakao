package webserver;

import utils.FileIoUtils;
import utils.MessageUtils;

import java.util.Optional;

public class ParseURI {
    private static final String QUESTION_MARK = "?";
    private static final String REGEX_BLANK = " ";
    private static final String REGEX_QUESTION_MARK = "\\?";
    private static final String REGEX_QUESTION_MARK_AND_PERIOD = "[?.]";

    private static final String DEFAULT_VIEW = "Hello World";

    private ParseURI() {
        throw new IllegalStateException(MessageUtils.UTILITY_CLASS);
    }

    private static String parseURI(String path) {
        return path.split(REGEX_BLANK)[1];
    }

    public static String getURI(String path) {
        return parseURI(path).split(REGEX_QUESTION_MARK_AND_PERIOD)[0];
    }

    public static Optional<String> getParams(String path) {
        if (!path.contains(QUESTION_MARK)) {
            return Optional.empty();
        }
        return Optional.of(parseURI(path).split(REGEX_QUESTION_MARK)[1]);
    }

    public static byte[] getBody(String path) {
        try {
            return FileIoUtils.loadFileFromClasspath(getViewPath(parseURI(path)));
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
        return "./templates" + path;
    }
}
