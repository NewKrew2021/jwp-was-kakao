package webserver.controller;

import webserver.config.ServerConfigConstants;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ResponseHeader;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResourceController extends HttpAbstractController{

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        ResponseHeader responseHeader = ResponseHeader.of(httpRequest);
        httpResponse.forward(responseHeader, ServerConfigConstants.STATIC_RESOURCE_PATH_PREFIX + httpRequest.getPath());
    }


}
