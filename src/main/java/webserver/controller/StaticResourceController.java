package webserver.controller;

import webserver.config.ServerConfigConstants;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ResponseHeader;
import webserver.http.ResponseStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResourceController implements HttpController {

    @Override
    public HttpResponse response(HttpRequest httpRequest) throws IOException, URISyntaxException {
        ResponseHeader responseHeader = ResponseHeader.of(httpRequest);
        return HttpResponse.from(ResponseStatus.OK, responseHeader, ServerConfigConstants.STATIC_RESOURCE_PATH_PREFIX + httpRequest.getPath());
    }


}
