package webserver.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ContentType {
    TEXT_HTML_UTF8("text/html;charset=utf-8", Arrays.asList(FileExtension.html)),
    NOTHING("", new ArrayList<>());


    private final String contentType;
    private final List<FileExtension> supportedFileExtentions;

    ContentType(String contentType, List<FileExtension> supportedFileExtensions) {
        this.contentType = contentType;
        this.supportedFileExtentions = supportedFileExtensions;
    }

    public String toString() {
        return contentType;
    }

    public static ContentType valueOf(FileExtension fileExtension){
        if( fileExtension == null ) return NOTHING;
        return Arrays.stream(ContentType.values())
                .filter(it -> it.supportedFileExtentions.contains(fileExtension))
                .findFirst()
                .orElse(NOTHING);
    }
}
