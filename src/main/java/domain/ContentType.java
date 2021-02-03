package domain;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/js"),
    ICO("image/x-icon"),
    JPEG("image/jpeg"),
    PLAIN("text/plain");

    private String mimeType;

    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static String of(String url) {
        if (url.endsWith(".html")) return HTML.mimeType;
        if (url.endsWith(".css")) return CSS.mimeType;
        if (url.endsWith(".js")) return JS.mimeType;
        if (url.endsWith(".ico")) return ICO.mimeType;
        if (url.endsWith(".jpeg")) return JPEG.mimeType;
        return PLAIN.mimeType;
    }
}
