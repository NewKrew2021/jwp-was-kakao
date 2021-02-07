package model;

import java.util.Locale;

public enum MIME {
    CSS("text/css"),
    EOT("application/vnd.ms-fontobject"),
    SVG("image/svg+xml"),
    TTF("font/ttf"),
    WOFF("font/woff"),
    WOFF2("font/woff2"),
    PNG("image/png"),
    JS("text/javascript"),
    HTML("text/html; charset=utf-8"),
    ICO("image/x-icon")
    ;

    private final String contentType;

    MIME(String contentType) {
        this.contentType = contentType;
    }

    public static String getContentType(String ext) {
        ext = ext.toUpperCase(Locale.ROOT);
        return MIME.valueOf(ext).contentType;
    }
}
