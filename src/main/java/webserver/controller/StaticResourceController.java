package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ResponseHeader;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResourceController extends HttpController {

    @Override
    public HttpResponse response(HttpRequest httpRequest) throws IOException, URISyntaxException {
        ResponseHeader responseHeader = new ResponseHeader();
        if(httpRequest.getPath().endsWith(".css")){
            responseHeader.addHeader("Content-Type", "text/css");
        }
        return new HttpResponse(responseHeader, setFile(httpRequest.getPath()));
    }


}
