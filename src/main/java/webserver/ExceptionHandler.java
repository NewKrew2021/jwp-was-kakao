package webserver;

import error.ErrorResponse;

public class ExceptionHandler {

    private ExceptionHandler() {
    }

    public static HttpResponse handle(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.from(exception);
        return HttpResponse.error(errorResponse);
    }
}
