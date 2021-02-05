package controller;

import annotation.web.RequestMethod;
import http.HttpRequest;
import http.StaticRequestPath;
import http.TemplateRequestExtension;

import java.util.Arrays;

public enum DispatchInfo {
    Template(new HttpRequest(RequestMethod.GET, ".html"), TemplateController.templateHandler),
    Static(new HttpRequest(RequestMethod.GET, "/css"), StaticController.staticHandler),
    UserCreate(new HttpRequest(RequestMethod.POST, "/user/create"), UserController.createUserHandler),
    UserLogin(new HttpRequest(RequestMethod.POST, "/user/login"), UserController.loginUserHandler),
    UserList(new HttpRequest(RequestMethod.GET, "/user/list"), UserController.listUserHandler),
    UserLogout(new HttpRequest(RequestMethod.GET, "/user/logout"), UserController.logoutUserHandler);

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
        if (matchWithTemplateRequest(request)) {
            return true;
        }
        if (matchWithStaticRequest(request)) {
            return true;
        }
        return matchWithRequest(request);
    }

    public boolean matchWithTemplateRequest(HttpRequest request) {
        return this.equals(Template)
                && request.hasSameMethod(httpRequest)
                && Arrays.stream(TemplateRequestExtension.values())
                    .anyMatch(extension -> request.endsWith(extension.getExtension()));
    }

    public boolean matchWithStaticRequest(HttpRequest request) {
        return this.equals(Static)
                && request.hasSameMethod(httpRequest)
                && Arrays.stream(StaticRequestPath.values())
                    .anyMatch(path -> request.startsWith(path.getPath()));
    }

    public boolean matchWithRequest(HttpRequest request) {
        return request.hasSameMethod(httpRequest)
                && request.hasSameUri(httpRequest);
    }
}
