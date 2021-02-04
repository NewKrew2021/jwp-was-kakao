package domain;

import java.nio.charset.StandardCharsets;

public class Response {
    private final HttpVersion httpVersion;
    private final StatusCode statusCode;
    private final ResponseHeaders headers;
    private final ResponseBody body;
    private final ContentType contentType;

    public Response(HttpVersion httpVersion,
                    StatusCode statusCode,
                    ResponseHeaders headers,
                    ResponseBody body,
                    ContentType contentType) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
        this.contentType = contentType;
    }

    public static Response ofDefaultFile(ResponseBody body, ContentType contentType) {
        ResponseHeaders headers = new ResponseHeaders();
        headers.addHeader("Content-Type", contentType.getType() + ";charset=utf-8");
        headers.addHeader("Content-Length", body.getByteSize() + "");
        return new Response(
                HttpVersion.HTTP1_1,
                StatusCode.OK,
                headers,
                body,
                contentType
        );
    }

    public static Response ofRedirect(String redirectUrl) {
        ResponseHeaders headers = new ResponseHeaders();
        headers.addHeader("Content-Type", ContentType.HTML + ";charset=utf-8");
        headers.addHeader("Location", redirectUrl);
        return new Response(
                HttpVersion.HTTP1_1,
                StatusCode.FOUND,
                headers,
                ResponseBody.ofEmptyBody(),
                ContentType.HTML
        );
    }

    public static Response ofDynamicHtml(ResponseBody responseBody) {
        ResponseHeaders headers = new ResponseHeaders();
        headers.addHeader("Content-Type", ContentType.HTML + ";charset=utf-8");
        headers.addHeader("Content-Length", responseBody.getByteSize() + "");
        return new Response(
                HttpVersion.HTTP1_1,
                StatusCode.OK,
                headers,
                responseBody,
                ContentType.HTML
        );

    }

    public void addHeader(String key, String value) {
        headers.addHeader(key, value);
    }

    public byte[] toBytes() {
        return this.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return httpVersion + " " + statusCode + "\r\n"
                + headers + "\r\n\r\n"
                + body + "\r\n\r\n";
    }
}