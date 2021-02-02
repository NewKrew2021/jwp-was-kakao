package web;

import org.springframework.http.MediaType;
import utils.DecodeUtils;
import utils.IOUtils;

import java.io.BufferedReader;

public class HttpBody {
    private static final HttpBody EMPTY_BODY = new HttpBody("");
    private final String body;

    public HttpBody(String body) {
        this.body = body;
    }

    public static HttpBody of(HttpHeaders httpHeaders, BufferedReader br) {
        String contentLength = httpHeaders.get(HttpHeaders.CONTENT_LENGTH);
        if (contentLength == null) {
            return HttpBody.empty();
        }

        String body = IOUtils.readData(br, Integer.parseInt(contentLength));
        String contentType = httpHeaders.get(HttpHeaders.CONTENT_TYPE);

        if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(contentType)) {
            body = DecodeUtils.decodeUrl(body);
        }

        return new HttpBody(body);
    }

    public static HttpBody empty() {
        return EMPTY_BODY;
    }

    public String getBody() {
        return body;
    }
}
