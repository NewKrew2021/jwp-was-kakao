package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import utils.IOUtils;

import java.io.InputStream;
import java.util.List;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final HttpMethod httpMethod;
    private final HttpUrl httpUrl;

    private HttpRequest(HttpMethod httpMethod, HttpUrl httpUrl) {
        this.httpMethod = httpMethod;
        this.httpUrl = httpUrl;
    }

    public static HttpRequest of(InputStream in) {
        List<String> texts = IOUtils.readRequest(in);
        String[] firstLine = texts.get(0).split(" ");
        logger.debug(String.join("\n", texts));
        return new HttpRequest(HttpMethod.valueOf(firstLine[0]), HttpUrl.of(firstLine[1]));
    }

    public HttpUrl getHttpUrl() {
        return httpUrl;
    }

    public boolean hasSameMethod(HttpMethod httpMethod) {
        return this.httpMethod == httpMethod;
    }
}
