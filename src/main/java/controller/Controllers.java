package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Controllers {

    public HttpResponse dispatch(HttpRequest request) throws IOException, URISyntaxException {
        return DispatchInfo.findMatchingHandler(request).handleRequest(request);
    }

}
