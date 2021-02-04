package controller.handler;

import exception.utils.NoFileException;
import model.HttpRequest;
import model.HttpResponse;

import java.io.IOException;

public class SecuredHandler implements Handler {
    private final Handler handler;

    public SecuredHandler(Handler handler) {
        this.handler = handler;
    }

    public HttpResponse handle(HttpRequest request) throws NoFileException, IOException {
        if (request.getCookie("logined") == null ||
                request.getCookie("logined").equals("false")) {
            return new HttpResponse().redirect("/user/login.html");
        }
        return handler.handle(request);
    }
}
