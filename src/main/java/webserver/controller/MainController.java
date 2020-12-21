package webserver.controller;

import webserver.config.ServerConfigConstants;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ResponseHeader;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainController extends HttpAbstractController {

    public static final String INDEX_HTML = "/index.html";
    public static final String FAVICON_ICO = "/favicon.ico";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        ResponseHeader responseHeader = ResponseHeader.of(httpRequest);
        if(httpRequest.getPath().endsWith(INDEX_HTML)){
            httpResponse.forward(responseHeader, ServerConfigConstants.TEMPLATES_RESOURCE_PATH_PREFIX+httpRequest.getPath());
        }else if(httpRequest.getPath().endsWith(FAVICON_ICO)){
            httpResponse.forward(responseHeader, ServerConfigConstants.TEMPLATES_RESOURCE_PATH_PREFIX+httpRequest.getPath());
        }else {
            httpResponse.error();
        }
    }


}
