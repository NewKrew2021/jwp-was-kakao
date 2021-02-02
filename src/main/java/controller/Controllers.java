package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Controllers {
    public HttpResponse dispatch(HttpRequest request) throws IOException, URISyntaxException {
        return findMatchingHandlers(request).handleRequest(request);
    }

    private Handler findMatchingHandlers(HttpRequest request) {
        return Arrays.stream(DispatchInfo.values())
                .filter(dispatchInfo -> dispatchInfo.matchWith(request))
                .findAny()
                .map(DispatchInfo::getRequestHandler)
                .orElseThrow(RuntimeException::new);
    }
}
