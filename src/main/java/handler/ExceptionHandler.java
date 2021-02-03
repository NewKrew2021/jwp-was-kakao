package handler;

import exception.FileCannotLoadedException;
import exception.FileNotExistException;
import exception.MapperNotFoundException;
import org.springframework.http.HttpStatus;
import web.HttpRequest;
import web.HttpResponse;
import webserver.HttpServlet;

public class ExceptionHandler implements HttpServlet {

    private final HttpServlet actual;

    public ExceptionHandler(HttpServlet actual) {
        this.actual = actual;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        try {
            return actual.service(httpRequest);
        } catch (FileNotExistException e) {
            HttpResponse response = HttpResponse.of(HttpStatus.NOT_FOUND);
            response.setBody(e.getMessage());
            return response;
        } catch (MapperNotFoundException e) {
            HttpResponse response = HttpResponse.of(HttpStatus.NOT_FOUND);
            response.setBody(e.getMessage());
            return response;
        } catch (FileCannotLoadedException e) {
            HttpResponse response = HttpResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setBody(e.getMessage());
            return response;
        } catch (Exception e) {
            return HttpResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        return actual.isSupport(httpRequest);
    }
}
