package model;

import utils.ParseUtils;

import java.util.Arrays;

public enum MimeType {
    JS("js", "text/javascript"),
    CSS("css", "text/css"),
    HTML("html", "text/html"),
    ICO("ico", "image/png"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg"),
    SVG("svg", "image/svg+xml"),
    EOT("eot", "application/vnd.ms-fontobject"),
    TTF("ttf", "application/x-font-ttf"),
    WOFF("woff", "application/font-woff"),
    WOFF2("woff2", "application/font-woff2"),
    DEFAULT("", "application/octet-stream");

    private final String extension;
    private final String contentType;

    MimeType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static MimeType getMimeType(String content) {
        return Arrays.stream(MimeType.values())
                .filter(mimeType -> mimeType.getExtension().equals(ParseUtils.parseExtension(content)))
                .findFirst()
                .orElse(DEFAULT);
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }
}
