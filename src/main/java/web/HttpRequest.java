package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import utils.DecodeUtils;
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
        logger.debug(String.join("\n", texts));

        HttpBody httpBody = create(httpHeaders, br);
        logger.debug("[Body] {}", httpBody.getBody());
        return new HttpRequest(HttpMethod.valueOf(firstLine[0]), httpHeaders, HttpUrl.of(firstLine[1]), httpBody);
    }

    private static HttpBody create(HttpHeaders httpHeaders, BufferedReader br) {
        String contentLength = httpHeaders.get("Content-Length");
        if (contentLength == null) {
            return HttpBody.empty();
        }

        String body = IOUtils.readData(br, Integer.parseInt(contentLength));
        String contentType = httpHeaders.get("Content-Type");

        if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(contentType)) {
            body = DecodeUtils.decodeUrl(body);
        }

        return new HttpBody(body);
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
