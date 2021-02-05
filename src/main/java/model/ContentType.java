package model;

public enum ContentType {
    HTML("html", "text/html"),
    JS("js", "application/javascript"),
    CSS("css", "text/css"),
    ICO("ico", "image/png"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpg"),
    JPEG("jpeg", "image/jpeg"),
    SVG("svg", "image/svg+xml"),
    EOT("eot", "font/eot"),
    TTF("ttf", "font/ttf"),
    WOFF("woff", "font/woff"),
    WOFF2("woff2", "font/woff2");

    private String extension;
    private String mimetype;

    ContentType(String extension, String mimetype) {
        this.extension = extension;
        this.mimetype = mimetype;
    }

    public String getExtension() {
        return extension;
    }

    public String getMimetype() {
        return mimetype;
    }
}
