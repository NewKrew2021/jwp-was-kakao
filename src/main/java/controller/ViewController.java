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

public class ViewController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private static final String basePath = "/";

    public void handleView(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + request.getPath());
        DataOutputStream dos = new DataOutputStream(out);
        Response.response200Header(dos, body.length);
        Response.responseBody(dos, body);
    }

    @Override
    public boolean hasSameBasePath(String path) {
        return basePath.equals(path.split("/")[1]);
    }

    @Override
    public void handle(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        handleView(request, out);
    }
}
