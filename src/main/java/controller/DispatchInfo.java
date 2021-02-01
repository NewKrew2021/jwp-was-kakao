package controller;

import annotation.web.RequestMethod;
import http.HttpRequest;

public enum DispatchInfo {
    Template(new HttpRequest(RequestMethod.GET, ".html"), TemplateController.htmlHandler),
    UserCreate(new HttpRequest(RequestMethod.POST, "/user/create"), UserController.createUserHandler),
    UserLogin(new HttpRequest(RequestMethod.POST, "/user/login"), UserController.loginUserHandler);

    private HttpRequest httpRequest;
    private Handler requestHandler;

    DispatchInfo(HttpRequest httpRequest, Handler requestHandler) {
        this.httpRequest = httpRequest;
        this.requestHandler = requestHandler;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public Handler getRequestHandler() {
        return requestHandler;
    }

    public boolean matchWith(HttpRequest request) {
        if(httpRequest.isTemplateRequest() && request.isTemplateRequest()) {
            return true;
        }
        return httpRequest.sameRequestLine(request);
    }
}
