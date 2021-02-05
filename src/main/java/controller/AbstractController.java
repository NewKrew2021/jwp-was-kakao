package controller;

import exception.InvalidFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

public abstract class AbstractController implements Controller {
    protected static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            if (httpRequest.getMethod() == HttpMethod.POST) {
                doPost(httpRequest, httpResponse);
                return;
            }
            if (httpRequest.getMethod() == HttpMethod.GET) {
                doGet(httpRequest, httpResponse);
                return;
            }
        } catch (InvalidFileException e) {
            logger.error(e.getMessage());
            httpResponse.notFound();
        }
        httpResponse.badRequest();
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.methodNotAllowed();
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.methodNotAllowed();
    }
}
