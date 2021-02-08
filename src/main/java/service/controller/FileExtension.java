package service.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum FileExtension {

    CSS(".css", "text/css", ResourceType.STATIC),
    PNG(".png", "image/png", ResourceType.STATIC),
    JS(".js", "text/js", ResourceType.STATIC),
    EOT(".eot", "application/vnd.ms-fontobject", ResourceType.STATIC),
    SVG(".svg", "image/svg+xml", ResourceType.STATIC),
    TTF(".ttf", "application/octet-stream", ResourceType.STATIC),
    WOFF(".woff", "application/font-woff", ResourceType.STATIC),
    WOFF2(".woff2", "application/font-woff", ResourceType.STATIC),
    ICO(".ico", "image/x-icon", ResourceType.TEMPLATE),
    HTML(".html", "text/html; charset=utf-8", ResourceType.TEMPLATE);

    private static Map<String, FileExtension> fileExtensions = new HashMap<>();

    private final String extension;
    private final String contentType;
    private final ResourceType resourceType;

    public enum ResourceType {
        STATIC,
        TEMPLATE;
    }

    static {
        for(FileExtension fileExtension : FileExtension.values()){
            fileExtensions.put(fileExtension.extension, fileExtension);
        }
    }

    FileExtension(String extension, String contentType, ResourceType resourceType) {
        this.extension = extension;
        this.contentType = contentType;
        this.resourceType = resourceType;
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public static FileExtension getFileExtensionToExtension(String extension){
        return Optional.of(fileExtensions.get(extension)).orElse(HTML);
    }

}
