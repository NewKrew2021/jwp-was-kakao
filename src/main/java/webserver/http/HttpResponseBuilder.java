package webserver.http;

import java.util.UUID;

public class HttpResponseBuilder {

    private HttpResponse response;

    public HttpResponseBuilder() {
        response = new HttpResponse();
    }

    public HttpResponseBuilder with200OK() {
        response.setResponseCode(HttpCode._200);
        return this;
    }

    public HttpResponseBuilder with302Redirect(String location) {
        response.setResponseCode(HttpCode._302);
        response.addHeader(HttpHeaders.LOCATION, location);
        return this;
    }

    public HttpResponseBuilder withBody(byte[] body) {
        response.setBody(body);
        return this;
    }

    public HttpResponseBuilder withSetCookie(SetCookie setCookie) {
        response.addHeader(HttpHeaders.SET_COOKIE, setCookie.toString());
        return this;
    }

    public HttpResponseBuilder withSessionId(UUID id) {
        SetCookie setCookie = new SetCookie(HttpSession.COOKIE_HTTP_SESSION_KEY, id.toString());
        setCookie.setPath("/");
        response.addHeader(HttpHeaders.SET_COOKIE, setCookie.toString());
        return this;
    }

    public HttpResponseBuilder withContentType(String contentType) {
        response.addHeader(HttpHeaders.CONTENT_TYPE, contentType);
        return this;
    }

    public HttpResponse build() {
        return response;
    }
}
