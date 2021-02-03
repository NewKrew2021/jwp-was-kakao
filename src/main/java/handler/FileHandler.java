package handler;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import web.HttpHeaders;
import web.HttpRequest;
import web.HttpResponse;
import web.HttpUrl;
import webserver.HttpServlet;

public abstract class FileHandler implements HttpServlet {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        HttpUrl httpUrl = httpRequest.getHttpUrl();
        byte[] body = getBody(httpUrl);
        HttpResponse httpResponse = HttpResponse.of(HttpStatus.OK);

        httpResponse.addHeader(HttpHeaders.CONTENT_TYPE, getContentType());
        httpResponse.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
        httpResponse.setBody(body);

        return httpResponse;
    }

    protected abstract String getContentType();

    protected abstract byte[] getBody(HttpUrl httpUrl);

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        return isSupport(httpRequest.getHttpUrl()) && httpRequest.hasSameMethod(HttpMethod.GET);
    }

    protected abstract boolean isSupport(HttpUrl httpUrl);
}
