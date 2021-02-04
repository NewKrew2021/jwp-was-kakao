package handler;

import utils.FileIoUtils;
import web.HttpUrl;

public class CssHandler extends FileHandler {
    @Override
    protected String getContentType() {
        return "text/css";
    }

    @Override
    protected byte[] getBody(HttpUrl httpUrl) {
        return FileIoUtils.loadFileFromClasspath("./static" + httpUrl.getUrl());
    }

    @Override
    protected boolean isSupport(HttpUrl httpUrl) {
        return httpUrl.endsWith(".css");
    }
}
