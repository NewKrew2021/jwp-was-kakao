package domain;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/js"),
    ICO("image/x-icon"),
    JPEG("image/jpeg");

    private final String mimeType;

    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static ContentType of(String url) {
        if (url.endsWith(".css")) return CSS;
        if (url.endsWith(".js")) return JS;
        if (url.endsWith(".ico")) return ICO;
        if (url.endsWith(".jpeg")) return JPEG;
        return HTML;
    }

    @Override
    public String toString() {
        return mimeType;
    }
}
