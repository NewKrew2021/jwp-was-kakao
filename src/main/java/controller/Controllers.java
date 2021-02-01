package controller;

import http.HttpRequest;

import java.io.DataOutputStream;
import java.util.Arrays;

public class Controllers {

    public void dispatch(HttpRequest request, DataOutputStream dos) {
        findMatchingHandlers(request).handleRequest(request, dos);
    }

    private RequestHandler findMatchingHandlers(HttpRequest request) {
        return Arrays.stream(DispatchInfo.values())
                .filter(dispatchInfo -> dispatchInfo.matchWith(request))
                .findAny()
                .map(DispatchInfo::getRequestHandler)
                .orElseThrow(RuntimeException::new);
    }
}
