package webserver.response;

import webserver.Cookie;
import webserver.request.ContentType;
import webserver.request.HttpRequest;
import webserver.request.Protocol;
import webserver.request.Status;

public class HttpResponse {

    private final ResponseHeader header;
    private final byte[] body;

    public HttpResponse(ResponseHeader header) {
        this.header = header;
        this.body = "".getBytes();
    }

    public HttpResponse(ResponseHeader header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    public static HttpResponse error() {
        return new HttpResponse(ResponseHeader.builder()
                .protocol(Protocol.HTTP)
                .status(Status.INTERNAL_SERVER_ERROR)
                .build());
    }

    public String getHeader() {
        return header.toString();
    }

    public byte[] getBody() {
        return body;
    }

    public static HttpResponse notFound() {
        return new HttpResponse(ResponseHeader.builder()
                .protocol(Protocol.HTTP)
                .status(Status.NOT_FOUND)
                .build());
    }

    public static HttpResponse file(byte[] bodyFromFile, ContentType contentType) {
        return new HttpResponse(
                ResponseHeader.builder()
                        .protocol(Protocol.HTTP)
                        .status(Status.OK)
                        .contentType(contentType)
                        .contentLength(bodyFromFile.length)
                        .build(),
                bodyFromFile
        );
    }

    public static HttpResponse redirect(HttpRequest request, String path) {
        return new HttpResponse(
                ResponseHeader.builder()
                        .protocol(Protocol.HTTP)
                        .status(Status.REDIRECT)
                        .location("http://" + request.getHeader().getHost() + path)
                        .build()
        );
    }

    public static HttpResponse redirectWithCookie(HttpRequest request, Cookie cookie, String path) {
        return new HttpResponse(
                ResponseHeader.builder()
                        .protocol(Protocol.HTTP)
                        .status(Status.REDIRECT)
                        .cookie(cookie)
                        .location("http://" + request.getHeader().getHost() + path)
                        .build()
        );
    }
}
