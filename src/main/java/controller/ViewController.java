package controller;

import model.HttpRequest;
import model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ViewController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private static final Map<MethodPath, Handler> handlers = new HashMap<>();

    private static final String basePath = "";

    {
        putHandler("/js/.*", "GET", this::handleJs);
        putHandler("/css/.*", "GET", this::handleCss);
        putHandler("/.*", "GET", this::handleView);
    }

    public static void putHandler(String path, String method, Handler handler) {
        handlers.put(new MethodPath(basePath + path, method), handler);
    }

    public void handleCss(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("{}", request.getPath());
        byte[] body = FileIoUtils.loadFileFromClasspath("./static" + request.getPath());
        DataOutputStream dos = new DataOutputStream(out);
        Response.response200CssHeader(dos, body.length);
        Response.responseBody(dos, body);
    }

    public void handleJs(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        log.info("{}", request.getPath());
        byte[] body = FileIoUtils.loadFileFromClasspath("./static" + request.getPath());
        DataOutputStream dos = new DataOutputStream(out);
        Response.response200JsHeader(dos, body.length);
        Response.responseBody(dos, body);
    }

    public void handleView(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates" +
                (request.getPath().equals("/") ? "/index.html" : request.getPath()));
        DataOutputStream dos = new DataOutputStream(out);
        Response.response200Header(dos, body.length);
        Response.responseBody(dos, body);
    }

    @Override
    public boolean hasSameBasePath(String path) {
        return path.startsWith(basePath);
    }

    @Override
    public boolean handle(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        for (Map.Entry<MethodPath, Handler> entry : handlers.entrySet()) {
            log.info("{} {}", request.getPath(), entry.getKey().getPath());
            if (request.getPath().matches(entry.getKey().getPath())) {
                log.info("matched *******************");
                entry.getValue().handle(request, out);
                return true;
            }
        }
        return false;
    }
}
