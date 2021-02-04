package controller;

import controller.handler.Handler;
import exception.utils.NoFileException;
import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
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

    public void putHandler(String path, HttpMethod method, Handler handler) {
        handlers.put(new PathInfo(basePath + path, method), handler);
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public boolean hasSameBasePath(String path) {
        return path.startsWith(basePath);
    }

    public HttpResponse handle(HttpRequest request) throws NoFileException, IOException {
        for (Map.Entry<PathInfo, Handler> entry : handlers.entrySet()) {
            log.debug("matching {} with controller {}", request.getPath(), entry.getKey().getPath());
            if (request.getPath().matches(entry.getKey().getPath())) {
                log.debug("handling {} with controller {}", request.getPath(), entry.getKey().getPath());
                return entry.getValue().handle(request);
            }
        }
        return null;
    }
}
