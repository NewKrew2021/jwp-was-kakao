package handler;

import utils.FileIoUtils;
import web.HttpUrl;

public class FontsHandler extends FileHandler {
    @Override
    protected String getContentType() {
        return "font/opentype";
    }

    @Override
    protected byte[] getBody(HttpUrl httpUrl) {
        return FileIoUtils.loadFileFromClasspath("./static" + httpUrl.getUrl());
    }

    @Override
    protected boolean isSupport(HttpUrl httpUrl) {
        return httpUrl.endsWith(".woff") || httpUrl.endsWith(".ttf");
    }
}
