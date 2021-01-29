package controller;

import model.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private static final String basePath = "user";

    Map<String, Handler> handlers = new HashMap<>();

    {
        handlers.put("/create", this::handleCreate);
    }

    public void handleCreate(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("handling Creation");
    }

    @Override
    public boolean hasSameBasePath(String path) {
        log.info("****************************{} {}", path, path.split("/")[1]);
        return basePath.equals(path.split("/")[1]);
    }

    @Override
    public void handle(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        for (Map.Entry<String, Handler> entry : handlers.entrySet()) {
            if (("/" + basePath + entry.getKey()).equals(request.getPath())) {
                entry.getValue().handle(request, out);
            }
        }
    }
}
