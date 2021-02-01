package controller;

import annotation.web.RequestMethod;
import http.HttpRequest;

public enum DispatchInfo {
    Template(new HttpRequest(RequestMethod.GET, ".html"), TemplateController.htmlHandler),
    UserCreate(new HttpRequest(RequestMethod.POST, "/user/create"), UserController.createUserHandler);

    private HttpRequest httpRequest;
    private RequestHandler requestHandler;

    DispatchInfo(HttpRequest httpRequest, RequestHandler requestHandler) {
        this.httpRequest = httpRequest;
        this.requestHandler = requestHandler;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

    public boolean matchWith(HttpRequest request) {
        if(httpRequest.isTemplateRequest() && request.isTemplateRequest()) {
            return true;
        }
        return httpRequest.sameRequestLine(request);
    }
}
