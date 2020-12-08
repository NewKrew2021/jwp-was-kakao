package webserver.controller;

import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ResponseHeader;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainController extends HttpController {

    public static final String INDEX_HTML = "/index.html";
    public static final String FAVICON_ICO = "/favicon.ico";

    public HttpResponse response(HttpRequest httpRequest) throws IOException, URISyntaxException {
        ResponseHeader responseHeader = new ResponseHeader();
        if(httpRequest.getPath().endsWith(INDEX_HTML)){
            responseHeader.addHeader("Content-Type", ContentType.HTML.toString());
            return new HttpResponse(responseHeader, setView(httpRequest.getPath()));
        }else if(httpRequest.getPath().endsWith(FAVICON_ICO)){
            return new HttpResponse(responseHeader, setView(httpRequest.getPath()));
        }else {
            return new HttpResponse().error();
        }
    }


}
