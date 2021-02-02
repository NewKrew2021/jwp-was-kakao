package web;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import utils.DecodeUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final HttpHeaders httpHeaders;
    private final HttpUrl httpUrl;
    private final HttpBody httpBody;

    private HttpRequest(HttpMethod httpMethod, HttpHeaders httpHeaders, HttpUrl httpUrl, HttpBody httpBody) {
        this.httpMethod = httpMethod;
        this.httpHeaders = httpHeaders;
        this.httpUrl = httpUrl;
        this.httpBody = httpBody;
    }

    public static HttpRequest of(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        List<String> texts = IOUtils.readUntilEmptyLine(br);
        HttpHeaders httpHeaders = HttpHeaders.of(texts.subList(1, texts.size()));
        String[] firstLine = texts.get(0).split(" ");

        return new HttpRequest(HttpMethod.valueOf(firstLine[0]), httpHeaders, HttpUrl.of(firstLine[1]), HttpBody.of(httpHeaders, br));
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

    public HttpBody getHttpBody() {
        return httpBody;
    }
}
