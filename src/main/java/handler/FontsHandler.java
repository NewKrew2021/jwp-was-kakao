package handler;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;
import web.HttpRequest;
import web.HttpResponse;
import web.HttpUrl;
import webserver.HttpServlet;

import java.nio.charset.StandardCharsets;

public class FontsHandler implements HttpServlet {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        HttpUrl httpUrl = httpRequest.getHttpUrl();
        String body = FileIoUtils.loadFileFromClasspath("./static" + httpUrl.getUrl(), StandardCharsets.ISO_8859_1);
        HttpResponse httpResponse = HttpResponse.of(HttpStatus.OK);

        httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
        httpResponse.addHeader("Content-Length", String.valueOf(body.length()));
        httpResponse.setBody(body);

        return httpResponse;
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        HttpUrl httpUrl = httpRequest.getHttpUrl();
        return (httpUrl.endsWith(".woff") || httpUrl.endsWith(".ttf")) && httpRequest.hasSameMethod(HttpMethod.GET);
    }
}
