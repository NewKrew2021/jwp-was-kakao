package webserver.service;

import annotation.web.ResponseStatus;
import user.exceptions.IllegalUserValuesException;
import webserver.http.*;

import java.util.NoSuchElementException;

public class ExceptionHandler extends AbstractController {
    private final Controller controller;

    public ExceptionHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            controller.service(httpRequest, httpResponse);
        } catch (IllegalUserValuesException | IllegalArgumentException e) {
            httpResponse.sendStatusWithBody(ResponseStatus.BAD_REQUEST, new Body(e.getMessage()));
        } catch (NoSuchElementException e) {
            httpResponse.sendStatusWithBody(ResponseStatus.NOT_FOUND, new Body(e.getMessage()));
        } catch (IllegalStateException ignored) {

        } catch (Exception e) {
            httpResponse.sendStatusWithBody(ResponseStatus.INTERNAL_SERVER_ERROR, new Body(e.getMessage()));
        }
    }
}
