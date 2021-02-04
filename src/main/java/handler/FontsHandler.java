package handler;

import utils.FileIoUtils;
import web.HttpUrl;

import java.nio.charset.StandardCharsets;

public class FontsHandler extends FileHandler {
    @Override
    protected String getContentType() {
        return "text/html;charset=utf-8";
    }

    @Override
    protected String getBody(HttpUrl httpUrl) {
        return FileIoUtils.loadFileFromClasspath("./static" + httpUrl.getUrl(), StandardCharsets.ISO_8859_1);
    }

    @Override
    protected boolean isSupport(HttpUrl httpUrl) {
        return httpUrl.endsWith(".woff") || httpUrl.endsWith(".ttf");
    }
}
