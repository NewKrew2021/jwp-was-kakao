package webserver.http.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpRequestUriLoader;
import webserver.http.HttpResponse;

public class Controllers {

    public static Controller NOT_FOUND = new NotFoundController();
    public static Controller STATIC_RESOURCE = new StaticResourceController();

    private static class NotFoundController implements Controller {
        @Override
        public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
            httpResponse.setStatusLine("HTTP/1.1 404 NotFound");
        }
    }

    private static class StaticResourceController implements Controller {

        private HttpRequestUriLoader uriLoader = HttpRequestUriLoader.fileLoader("./static");

        @Override
        public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
            byte[] body = uriLoader.load(httpRequest.getPath());
            httpResponse.setBody(body);
        }
    }
}
