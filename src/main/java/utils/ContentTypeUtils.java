package utils;

public enum ContentTypeUtils {
    CSS("/css/", "./static", "src/main/resources/static", "text/css"),
    HTML("/", "./templates", "src/main/resources/templates", "text/html;charset=UTF-8"),
    JS("/js/", "./static", "src/main/resources/static", "application/js"),
    FORM("/fonts/", "./static", "src/main/resources/static", "application/x-www-form-urlencoded");

    private final String pathContain;
    private final String pathPrefix;
    private final String filePath;
    private final String contentType;

    ContentTypeUtils(String pathContain, String pathPrefix, String filePath, String contentType) {
        this.pathContain = pathContain;
        this.pathPrefix = pathPrefix;
        this.filePath = filePath;
        this.contentType = contentType;
    }

    public String getPathContain() {
        return pathContain;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getContentType() {
        return contentType;
    }

}
