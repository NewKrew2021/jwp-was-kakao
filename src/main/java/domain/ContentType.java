package domain;

import java.util.Arrays;

public enum ContentType {

    HTML("text/html", "./templates"),
    CSS("text/css", "./static"),
    JS("application/js", "./static"),
    ICO("image/x-icon", "./static"),
    JPEG("image/jpeg", "./static"),
    TTF("application/x-font-ttf", "./static"),
    WOFF("application/x-font-woff", "./static"),
    PLAIN("text/plain", "./static");

    private final String mimeType;
    private final String prefix;

    ContentType(String mimeType, String prefix) {
        this.mimeType = mimeType;
        this.prefix = prefix;
    }

    public static ContentType of(String url) {
        String extension = extractExtension(url);
        return Arrays.stream(values())
                .filter(contentType -> contentType.name().equals(extension))
                .findAny()
                .orElse(PLAIN);
    }

    public static String prefixOfUrl(String url) {
        String extension = extractExtension(url);
        return Arrays.stream(values())
                .filter(contentType -> contentType.name().equals(extension))
                .findAny()
                .orElse(PLAIN)
                .getPrefix();
    }

    private static String extractExtension(String url) {
        return url.substring(url.lastIndexOf('.') + 1).toUpperCase();
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getPrefix() {
        return prefix;
    }

}
