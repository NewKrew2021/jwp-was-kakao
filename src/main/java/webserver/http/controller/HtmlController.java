package webserver.http.controller;

import utils.ClasspathFileLoader;
import utils.FileLoader;
import webserver.http.*;

public class HtmlController implements Controller {

    private FileLoader uriLoader = new ClasspathFileLoader("./templates");

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] body = uriLoader.load(httpRequest.getPath());
        httpResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);
        httpResponse.setBody(body);
    }

}
