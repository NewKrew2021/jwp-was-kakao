package model.response;

import java.util.HashMap;
import java.util.Map;

public enum ContentType {
    js("text/js"),
    html("text/html"),
    css("text/css"),
    woff("application/font-woff"),
    ttf("application/x-font-ttf"),
    ico("image/x-icon");

    private final String contentType;
    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExtension(){
        return ".".concat(name());
    }

    public String getContentType(){
        return contentType;
    }
}
