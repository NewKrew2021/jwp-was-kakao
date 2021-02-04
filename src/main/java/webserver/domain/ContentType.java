package webserver.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum ContentType {
    HTML("html", "text/html"),
    JS("js", "text/javascript"),
    XML("xml", "text/xml"),
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
    private String mimeType;
    private static Map<String, String> contentTypes;

    static {
        contentTypes = Arrays.stream(values())
                .collect(Collectors.toMap(ContentType::getExtension, ContentType::getMimeType));
    }

    ContentType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public static String getMimeTypeOf(String extension) {
        return contentTypes.get(extension);
    }

    public String getExtension() {
        return extension;
    }

    public String getMimeType() {
        return mimeType;
    }
}
