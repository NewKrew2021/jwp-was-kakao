package webserver.http;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;

public class HttpResponse {
    private static final String STATUS_PREFIX = "HTTP/1.1";

    private ResponseStatus status;
    private ResponseHeader header;
    private byte[] body;
    private final DataOutputStream dos;

    public HttpResponse(final OutputStream out) {
        this.status = ResponseStatus.OK;
        this.header = ResponseHeader.create();
        this.body = "".getBytes();
        this.dos = new DataOutputStream(out);
    }

    public static HttpResponse newInstance(final OutputStream out){
        return new HttpResponse(out);
    }

    public void error(){
        this.status = ResponseStatus.NOT_FOUND;
        responseBody();
    }

    public void forward(byte[] body) {
        this.status = ResponseStatus.OK;
        this.body = body;
        responseBody();
    }

    public void forward(ResponseHeader header, String path) throws IOException, URISyntaxException {
        this.status = ResponseStatus.OK;
        this.header = header;
        this.body = FileIoUtils.loadFileFromClasspath(path);
        responseBody();
    }

    public void sendRedirect(ResponseHeader header, String path) throws IOException, URISyntaxException {
        header.addHeader("Location", path);
        this.status = ResponseStatus.FOUND;
        this.header = header;
        responseBody();
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


    private String makeStatus(ResponseStatus httpStatus) {
        return String.format("%s %s %s \r\n", STATUS_PREFIX, httpStatus.getCode(), httpStatus.getText());
    }

    private void responseBody() {
        try {
            dos.writeBytes(makeStatus(status));
            List<String> headers = header.makeHeader();
            for (String header : headers) {
                dos.writeBytes(header);
            }
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
