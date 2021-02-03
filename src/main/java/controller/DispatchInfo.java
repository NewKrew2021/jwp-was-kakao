package controller;

import annotation.web.RequestMethod;
import http.HttpRequest;

import java.util.Arrays;

public enum DispatchInfo {
    Template(new HttpRequest(RequestMethod.GET, ".html"), TemplateController.htmlHandler),
    Favicon(new HttpRequest(RequestMethod.GET, "/favicon.ico"), TemplateController.faviconHandler),
    Static(new HttpRequest(RequestMethod.GET, "/css"), StaticController.cssHandler),
    UserCreate(new HttpRequest(RequestMethod.POST, "/user/create"), UserController.createUserHandler),
    UserLogin(new HttpRequest(RequestMethod.POST, "/user/login"), UserController.loginUserHandler),
    UserList(new HttpRequest(RequestMethod.GET, "/user/list"), UserController.listUserHandler);

    private HttpRequest httpRequest;
    private Handler requestHandler;

    DispatchInfo(HttpRequest httpRequest, Handler requestHandler) {
        this.httpRequest = httpRequest;
        this.requestHandler = requestHandler;
    }

    public static Handler findMatchingHandler(HttpRequest request) {
        return Arrays.stream(values())
                .filter(dispatchInfo -> dispatchInfo.matchWith(request))
                .findAny()
                .map(DispatchInfo::getRequestHandler)
                .orElseThrow(RuntimeException::new);
    }

    public Handler getRequestHandler() {
        return requestHandler;
    }

    public boolean matchWith(HttpRequest request) {
        if (httpRequest.isTemplateRequest() && request.isTemplateRequest()) {
            return true;
        }
        if (httpRequest.isStaticRequest() && request.isStaticRequest()) {
            return true;
        }
        return httpRequest.sameRequestLine(request);
    }
}
