package webserver.http;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponse {
    private ResponseStatus status;
    private final ResponseHeader header;
    private final byte[] body;

    private HttpResponse(ResponseStatus responseStatus) {
        this.status = responseStatus;
        this.header = ResponseHeader.create();
        this.body = "".getBytes();
    }

    private HttpResponse(ResponseStatus responseStatus, ResponseHeader header) {
        this.status = responseStatus;
        this.header = header;
        this.body = "".getBytes();
    }

    private HttpResponse(ResponseStatus responseStatus, ResponseHeader header, byte[] body) {
        this.status = responseStatus;
        this.header = header;
        this.body = body;
    }

    public static HttpResponse from(byte[] body) {
        return new HttpResponse(ResponseStatus.OK, ResponseHeader.create(), body);
    }

    public static HttpResponse from(ResponseStatus responseStatus, ResponseHeader header, String viewName) throws IOException, URISyntaxException {
        return new HttpResponse(responseStatus, header, FileIoUtils.loadFileFromClasspath(viewName));
    }

    public static HttpResponse error(){
        return new HttpResponse(ResponseStatus.NOT_FOUND);
    }

    public static HttpResponse redirect(ResponseHeader header, String path) throws IOException, URISyntaxException {
        header.addHeader("Location", path);
        return new HttpResponse(ResponseStatus.FOUND, header);
    }

    public byte[] getBody() {
        return body;
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public ResponseStatus getStatus() {
        return status;
    }

}
