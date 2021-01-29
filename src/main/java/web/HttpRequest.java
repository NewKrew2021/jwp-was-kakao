package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final HttpMethod httpMethod;
    private final HttpHeaders httpHeaders;
    private final HttpUrl httpUrl;

    private HttpRequest(HttpMethod httpMethod, HttpHeaders httpHeaders, HttpUrl httpUrl) {
        this.httpMethod = httpMethod;
        this.httpHeaders = httpHeaders;
        this.httpUrl = httpUrl;
    }

    public static HttpRequest of(InputStream in) {
        List<String> texts = IOUtils.readRequestUntilHeader(new BufferedReader(new InputStreamReader(in)));
        HttpHeaders httpHeaders = HttpHeaders.of(texts.subList(1, texts.size()));
        String[] firstLine = texts.get(0).split(" ");
        logger.debug(String.join("\n", texts));
        return new HttpRequest(HttpMethod.valueOf(firstLine[0]), httpHeaders, HttpUrl.of(firstLine[1]));
    }

    public boolean hasSameMethod(HttpMethod httpMethod) {
        return this.httpMethod == httpMethod;
    }

    public HttpUrl getHttpUrl() {
        return httpUrl;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }
}
