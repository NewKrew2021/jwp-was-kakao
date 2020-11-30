package webserver;

import java.util.stream.Stream;

enum MimeType {
    APPLICATION_JS("application/js", "js"),
    TEXT_CSS("text/css", "css"),
    IMAGE_PNG("image/png", "png"),
    IMAGE_SVG("image/svg+xml", "svg"),
    FONT_TTF("font/ttf", "ttf"),
    FONT_WOFF("font/woff", "woff"),
    FONT_WOFF2("font/woff2", "woff2"),
    FONT_EOT("application/vnd.ms-fontobject", "eot"),
    TEXT_HTML("text/html", "html");

    private final String mimeTypeValue;
    private final String fileExtension;

    MimeType(String mimeTypeValue, String fileExtension) {
        this.mimeTypeValue = mimeTypeValue;
        this.fileExtension = fileExtension;
    }

    public String getMimeTypeValue() {
        return mimeTypeValue;
    }

    public static MimeType fromFileName(String fileName) {
        return Stream.of(values())
                .filter(mimeType -> fileName.endsWith(mimeType.fileExtension))
                .findAny()
                .orElse(MimeType.TEXT_HTML);
    }
}
