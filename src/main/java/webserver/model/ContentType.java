package webserver.model;

import java.util.Arrays;

public enum ContentType {
    HTML("html", "text/html;charset=utf-8"),
    CSS("css", "text/css"),
    JS("js", "text/javascript"),
    PNG("png", "image/png"),
    UNKNOWN("", "application/octet-stream");

    private static final String EXTENSION_PATTERN = ".*\\.([^.]+)";
    private final String extension;
    private final String mimeType;

    ContentType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public static ContentType fromUrlPath(String urlPath) {
        String extension = urlPath.replaceAll(EXTENSION_PATTERN, "$1");
        return Arrays.stream(values())
                .filter(contentType ->  contentType.extension.equals(extension))
                .findAny().orElse(UNKNOWN);
    }

    public String getMimeType() {
        return mimeType;
    }
}
