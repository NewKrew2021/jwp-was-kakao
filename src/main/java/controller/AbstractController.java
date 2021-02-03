package controller;

import exception.InvalidFileException;
import exception.UserAuthorizationException;
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
            }
            if (httpRequest.getMethod() == HttpMethod.GET) {
                doGet(httpRequest, httpResponse);
            }
        } catch (UserAuthorizationException e) {
            logger.error(e.getMessage());
            httpResponse.sendRedirect("/user/login.html");
        } catch (InvalidFileException e) {
            logger.error(e.getMessage());
            httpResponse.notFound();
        }
        httpResponse.badRequest();
    }

    public abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);

    public abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);
}
