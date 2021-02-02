package handler;

import utils.FileIoUtils;
import web.HttpUrl;

public class JsHandler extends FileHandler {
    @Override
    protected String getContentType() {
        return "text/html;charset=utf-8";
    }

    @Override
    protected String getBody(HttpUrl httpUrl) {
        return FileIoUtils.loadFileFromClasspath("./static" + httpUrl.getUrl());
    }

    @Override
    protected boolean isSupport(HttpUrl httpUrl) {
        return httpUrl.endsWith(".js");
    }
}
