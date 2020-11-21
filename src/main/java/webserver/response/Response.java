package webserver.response;

import webserver.request.ContentType;
import webserver.request.Protocol;
import webserver.request.Request;
import webserver.request.Status;

public class Response {

    private final ResponseHeader header;
    private final byte[] body;

    public Response(ResponseHeader header) {
        this.header = header;
        this.body = "".getBytes();
    }

    public Response(ResponseHeader header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    public static Response error() {
        return new Response(ResponseHeader.builder()
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

    public static Response notFound() {
        return new Response(ResponseHeader.builder()
                .protocol(Protocol.HTTP)
                .status(Status.NOT_FOUND)
                .build());
    }

    public static Response file(byte[] bodyFromFile, ContentType contentType) {
        return new Response(
                ResponseHeader.builder()
                        .protocol(Protocol.HTTP)
                        .status(Status.OK)
                        .contentType(contentType)
                        .contentLength(bodyFromFile.length)
                        .build(),
                bodyFromFile
        );
    }

    public static Response redirect(Request request, String path) {
        return new Response(
                ResponseHeader.builder()
                        .protocol(Protocol.HTTP)
                        .status(Status.REDIRECT)
                        .location("http://" + request.getHeader().getHost() + path)
                        .build()
        );
    }

    public static Response redirectWithCookie(Request request, String cookie, String path) {
        return new Response(
                ResponseHeader.builder()
                        .protocol(Protocol.HTTP)
                        .status(Status.REDIRECT)
                        .cookie(cookie, "/")
                        .location("http://" + request.getHeader().getHost() + path)
                        .build()
        );
    }
}
