package webserver.http;

public class HttpResponseBuilder {

    private HttpResponse response;

    public HttpResponseBuilder() {
        response = new HttpResponse();
    }

    public HttpResponseBuilder with302Redirect(String location) {
        response.setResponseCode(HttpCode._302);
        response.addHeader(HttpHeaders.LOCATION, location);
        return this;
    }

    public HttpResponseBuilder withSetCookie(SetCookie setCookie) {
        response.addHeader(HttpHeaders.SET_COOKIE, setCookie.toString());
        return this;
    }

    public HttpResponse build() {
        return response;
    }
}
