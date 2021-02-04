package controller;

import controller.handler.Handler;
import exception.utils.NoFileException;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.PathInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Controller {
    private static final Logger log = LoggerFactory.getLogger( Controller.class );
    protected final Map<PathInfo, Handler> handlers = new LinkedHashMap<>();
    protected String basePath = "";

    public void putHandler(String path, String method, Handler handler) {
        handlers.put(new PathInfo(basePath + path, method), handler);
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public boolean hasSameBasePath(String path) {
        return path.startsWith(basePath);
    }

    public boolean handle(HttpRequest request, HttpResponse response) throws NoFileException, IOException {
        for (Map.Entry<PathInfo, Handler> entry : handlers.entrySet()) {
            log.info("matching {} with controller {}", request.getPath(), entry.getKey().getPath());
            if (request.getPath().matches(entry.getKey().getPath())) {
                log.info("handling {} with controller {}", request.getPath(), entry.getKey().getPath());
                entry.getValue().handle(request, response);
                return true;
            }
        }
        return false;
    }
}
