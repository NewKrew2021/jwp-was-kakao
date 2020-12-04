package webserver.http;

public class DefaultExceptionHandler implements ExceptionHandler {

    private ExceptionHandlerResolver exceptionHandlerResolver;

    public DefaultExceptionHandler(ExceptionHandlerResolver exceptionHandlerResolver) {
        this.exceptionHandlerResolver = exceptionHandlerResolver;
    }

    @Override
    public void handle(Exception e, HttpRequest httpRequest, HttpResponse httpResponse) {
        ExceptionHandler handler = exceptionHandlerResolver.resolve(e);
        handler.handle(e, httpRequest,httpResponse);
    }

}
