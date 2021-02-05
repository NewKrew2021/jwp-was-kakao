package view;

import utils.FileIoUtils;
import webserver.Model;
import webserver.View;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticView implements View {
    private static final String prefix = "./static";

    @Override
    public boolean canHandle(String name) {
        return "static".equals(name);
    }

    @Override
    public void render(Model model, HttpRequest request, HttpResponse httpResponse) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(prefix + request.getUri());
            String mimeType = FileIoUtils.getMimeType(prefix + request.getUri());
            httpResponse.setBody(body, mimeType);
            httpResponse.setStatus("HTTP/1.1 200 OK");
        } catch (IOException | URISyntaxException e) {
            httpResponse.setStatus("HTTP/1.1 500 Internal Server Error");
        }
    }
}
