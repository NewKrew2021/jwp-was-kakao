package utils;

import controller.DispatchInfo;
import controller.Handler;
import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Dispatcher {
    public static HttpResponse dispatch(HttpRequest request) throws IOException, URISyntaxException {
        return findMatchingHandlers(request).handleRequest(request);
    }

    public static Handler findMatchingHandlers(HttpRequest request) {
        return Arrays.stream(DispatchInfo.values())
                .filter(dispatchInfo -> dispatchInfo.matchWith(request))
                .findAny()
                .map(DispatchInfo::getRequestHandler)
                .orElseThrow(RuntimeException::new);
    }
}
