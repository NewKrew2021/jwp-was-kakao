package controller.handler;

import exception.utils.NoFileException;
import model.request.HttpRequest;
import model.response.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;

public class SecuredHandler implements Handler {
    private final Handler handler;

    public SecuredHandler(Handler handler) {
        this.handler = handler;
    }

    public void handle(HttpRequest request, HttpResponse response) throws NoFileException, IOException {
        if (request.getCookie("logined") == null ||
                request.getCookie("logined").equals("false")) {
            response.sendRedirect("/user/login.html");
            return;
        }
        handler.handle(request, response);
    }
}
