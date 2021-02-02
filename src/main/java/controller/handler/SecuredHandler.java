package controller.handler;

import model.HttpRequest;
import model.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class SecuredHandler implements Handler {
    private final Handler handler;

    public SecuredHandler(Handler handler) {
        this.handler = handler;
    }

    public void handle(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        if (request.getCookie("logined") == null ||
                request.getCookie("logined").equals("false")) {
            HttpResponse.of(out).sendRedirect("/user/login.html");
            return;
        }
        handler.handle(request, out);
    }
}
