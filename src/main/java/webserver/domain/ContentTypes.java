package webserver.domain;

import java.util.Arrays;

public enum ContentTypes {
    CSS("text/css", "css"),
    JS("application/js", "js"),
    HTML("text/html", "html"),
    ICO("image/x-icon", "ico"),
    PNG("image/png", "png"),
    TTF("application/x-font-ttf", "ttf"),
    WOFF("application/x-font-woff", "woff"),
    DEFAULT("application/octet-stream", "");

    private final String type;
    private final String extension;

    ContentTypes(String type, String extension) {
        this.type = type;
        this.extension = extension;
    }

    public static ContentTypes getTypeFromPath(String path) {
        String extension = getExtenstionFromPath(path);
        return Arrays.stream(ContentTypes.values())
                .filter(contentTypes -> contentTypes.getExtension().equals(extension))
                .findAny()
                .orElse(DEFAULT);
    }

    private static String getExtenstionFromPath(String path) {
        int index = path.lastIndexOf(".");
        return path.substring(index + 1);
    }

    public String getType() {
        return type;
    }

    public String getExtension() {
        return extension;
    }
}
