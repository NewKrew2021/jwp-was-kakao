package webserver.http.controller;

import webserver.http.*;

public class Controllers {

    public static Controller NOT_FOUND = new NotFoundController();

    private static class NotFoundController implements Controller {
        @Override
        public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
            httpResponse.setStatus(HttpStatus.x404_NotFound);
        }
    }

}
