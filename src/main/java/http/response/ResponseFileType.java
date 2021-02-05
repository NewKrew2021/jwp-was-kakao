package http.response;

import exceptions.InvalidFileTypeException;

import java.util.Arrays;

public enum ResponseFileType {
    HTML("templates/", "text/html", "html"),
    JS("static/", "text/javascript", "javascript"),
    CSS("static/", "text/css", "css");

    private final String filePathPrefix;
    private final String contentType;
    private final String fileType;

    ResponseFileType(String filePath, String contentType, String fileType) {
        this.filePathPrefix = filePath;
        this.contentType = contentType;
        this.fileType = fileType;
    }

    public static ResponseFileType of(String type) {
        return Arrays.stream(ResponseFileType.values())
                .filter(fileType -> fileType.isSameFileType(type))
                .findAny()
                .orElseThrow(() -> new InvalidFileTypeException(type));
    }

    private boolean isSameFileType(String type) {
        return this.fileType.equals(type);
    }

    public String getFilePathPrefix() {
        return filePathPrefix;
    }

    public String getContentType() {
        return contentType;
    }
}
