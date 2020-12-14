package webserver.controller;

import webserver.config.ServerConfigConstants;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ResponseHeader;
import webserver.http.ResponseStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainController implements HttpController {

    public static final String INDEX_HTML = "/index.html";
    public static final String FAVICON_ICO = "/favicon.ico";

    public HttpResponse response(HttpRequest httpRequest) throws IOException, URISyntaxException {
        ResponseHeader responseHeader = ResponseHeader.of(httpRequest);
        if(httpRequest.getPath().endsWith(INDEX_HTML)){
            return HttpResponse.from(ResponseStatus.OK, responseHeader, ServerConfigConstants.TEMPLATES_RESOURCE_PATH_PREFIX + httpRequest.getPath());
        }else if(httpRequest.getPath().endsWith(FAVICON_ICO)){
            return HttpResponse.from(ResponseStatus.OK, responseHeader, ServerConfigConstants.TEMPLATES_RESOURCE_PATH_PREFIX + httpRequest.getPath());
        }else {
            return HttpResponse.error();
        }
    }


}
