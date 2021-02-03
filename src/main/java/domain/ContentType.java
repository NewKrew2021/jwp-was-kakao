package domain;

public enum ContentType {
    HTML("text/html", ".html"),
    CSS("text/CSS", ".css"),
    JS("application/javascript", ".js"),
    WOFF("font/woff", ".woff"),
    ICO("image/x-icon", ".ico"),
    SVG("image/svg+xml", ".svg"),
    TTF("font/ttf", ".ttf");

    private final String type;
    private final String extension;

    ContentType(String type, String extension) {
        this.type = type;
        this.extension = extension;
    }

    public ContentType ofDefaultType() {
        return HTML;
    }

    public String getType() {
        return type;
    }
}
