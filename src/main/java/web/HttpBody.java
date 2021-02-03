package web;

import org.springframework.http.MediaType;
import utils.DecodeUtils;
import utils.IOUtils;

import java.io.BufferedReader;

public class HttpBody {
    private static final HttpBody EMPTY_BODY = new HttpBody(null);

    private final byte[] body;

    public HttpBody(byte[] body) {
        this.body = body;
    }

    public static HttpBody of(HttpHeaders httpHeaders, BufferedReader br) {
        String contentLength = httpHeaders.get(HttpHeaders.CONTENT_LENGTH);
        if (contentLength == null) {
            return HttpBody.empty();
        }

        String contentType = httpHeaders.get(HttpHeaders.CONTENT_TYPE);
        String body = IOUtils.readData(br, Integer.parseInt(contentLength));
        if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(contentType)) {
            body = DecodeUtils.decodeUrl(body);
        }

        return new HttpBody(body.getBytes());
    }

    public static HttpBody empty() {
        return EMPTY_BODY;
    }

    public byte[] getBody() {
        return body;
    }
}
