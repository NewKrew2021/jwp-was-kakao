package controller;

import annotation.web.RequestMethod;
import domain.HttpRequest;
import domain.HttpResponse;
import exception.HttpException;
import org.springframework.http.HttpStatus;

public abstract class AbstractController implements Controller {
    public HttpResponse service(HttpRequest httpRequest) throws HttpException {
        if (httpRequest.getMethod() == RequestMethod.GET) {
            return doGet(httpRequest);

        }
        if(httpRequest.getMethod() == RequestMethod.POST) {
            return doPost(httpRequest);
        }
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    HttpResponse doGet(HttpRequest httpRequest) throws HttpException {
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    HttpResponse doPost(HttpRequest httpRequest) throws HttpException {
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
