package webserver.http.controller;

import webserver.http.*;

public class TemplateController implements Controller {

    private HttpRequestUriLoader uriLoader = HttpRequestUriLoader.fileLoader("./templates");

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] body = uriLoader.load(httpRequest.getPath());
        httpResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);
        httpResponse.setBody(body);
    }

}
