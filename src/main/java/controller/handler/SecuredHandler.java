package controller.handler;

import model.HttpRequest;
import model.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class SecuredHandler implements Handler {

    Handler handler;

    public SecuredHandler(Handler handler) {
        this.handler = handler;
    }

    public void handle(HttpRequest request, OutputStream out) throws URISyntaxException, IOException {
        DataOutputStream dos = new DataOutputStream(out);
        if (request.getCookie("logined") == null || request.getCookie("logined").equals("false")) {
            HttpResponse.response302Header(dos, "/user/login.html");
            HttpResponse.responseWithoutBody(dos);
            return;
        }
        handler.handle(request, out);
    }
}
