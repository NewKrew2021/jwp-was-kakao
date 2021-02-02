package webserver.domain;

import org.apache.commons.io.FilenameUtils;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class Body {
    private static final HashMap<String, String> contentTypes = new HashMap<String, String>() {{
        put("html", "text/html");
        put("js", "text/javascript");
        put("xml", "text/xml");
        put("css", "text/css");
        put("ico", "image/png");
        put("png", "image/png");
        put("jpg", "image/jpg");
        put("jpeg", "image/jpeg");
        put("svg", "image/svg+xml");
        put("eot", "font/eot");
        put("ttf", "font/ttf");
        put("woff", "font/woff");
        put("woff2", "font/woff2");
    }};
    private final int contentLength;
    private final String contentType;
    private final byte[] body;

    public Body(String path) {
        try {
            body = FileIoUtils.loadFileFromClasspath(getRootDirectory(path) + path);
            contentType = identifyContentType(path);
            contentLength = body.length;
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("Path for body is not correct");
        }
    }

    public Body(byte[] body, String contentType) {
        this.body = body;
        this.contentType = contentType;
        contentLength = body.length;
    }

    private String getRootDirectory(String path) {
        String extension = FilenameUtils.getExtension(path);
        if (extension.equals("html") || extension.equals("ico")) {
            return "./templates";
        }
        return "./static";
    }

    private String identifyContentType(String path) throws IOException {
        return contentTypes.get(FilenameUtils.getExtension(path));
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }
}
