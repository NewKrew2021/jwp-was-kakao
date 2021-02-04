package web;

import org.springframework.http.HttpMethod;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class HttpRequest {
    private static final int START_LINE = 0;
    private static final int METHOD = 0;
    private static final int URL = 1;
    private static final String START_LINE_DELIMITER = " ";

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
        List<String> lines = IOUtils.readUntilEmptyLine(br);
        String[] startLine = lines.get(START_LINE).split(START_LINE_DELIMITER);
        HttpHeaders httpHeaders = HttpHeaders.of(lines.subList(START_LINE + 1, lines.size()));

        return new HttpRequest(HttpMethod.valueOf(startLine[METHOD]), httpHeaders, HttpUrl.of(startLine[URL]), HttpBody.of(httpHeaders, br));
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
