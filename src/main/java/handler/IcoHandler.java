package handler;

import utils.FileIoUtils;
import web.HttpUrl;

public class IcoHandler extends FileHandler {
    @Override
    protected String getContentType() {
        return "image/x-icon";
    }

    @Override
    protected byte[] getBody(HttpUrl httpUrl) {
        return FileIoUtils.loadFileFromClasspath("./templates" + httpUrl.getUrl());
    }

    @Override
    protected boolean isSupport(HttpUrl httpUrl) {
        return httpUrl.endsWith(".ico");
    }
}
