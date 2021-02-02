package handler;

import org.springframework.http.HttpStatus;
import web.HttpRequest;
import web.HttpResponse;
import webserver.HttpServlet;

public class NotAllowedMethodHandler implements HttpServlet {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        return HttpResponse.of(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        return false;
    }
}
