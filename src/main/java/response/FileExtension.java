package response;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum FileExtension {

    CSS(".css", "text/css", Constants.STATIC),
    PNG(".png", "image/png", Constants.STATIC),
    JS(".js", "text/js", Constants.STATIC),
    EOT(".eot", "application/vnd.ms-fontobject", Constants.STATIC),
    SVG(".svg", "image/svg+xml", Constants.STATIC),
    TTF(".ttf", "application/octet-stream", Constants.STATIC),
    WOFF(".woff", "application/font-woff", Constants.STATIC),
    WOFF2(".woff2", "application/font-woff", Constants.STATIC),
    ICO(".ico", "image/x-icon", Constants.TEMPLATE),
    HTML(".html", "text/html", Constants.TEMPLATE);

    private static Map<String, FileExtension> fileExtensions = Arrays.stream(values())
            .collect(Collectors.toMap(FileExtension::getExtension, Function.identity()));

    private String extension;
    private String contentType;
    private String filePath;

    private static class Constants {
        public static final String STATIC = "./static";
        public static final String TEMPLATE = "./templates";
    }

    FileExtension(String extension, String contentType, String filePath) {
        this.extension = extension;
        this.contentType = contentType;
        this.filePath = filePath;
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFilePath() {
        return filePath;
    }

    public static FileExtension getFileExtensionToExtension(String extension) {
        return fileExtensions.getOrDefault(extension, HTML);
    }

}
