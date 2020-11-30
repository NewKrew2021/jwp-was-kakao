package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final HttpCode responseCode;
    private byte[] body;

    public HttpResponse(HttpCode responseCode) {
        this.responseCode = responseCode;
    }

    public HttpResponse(HttpCode responseCode, byte[] body) {
        this.responseCode = responseCode;
        this.body = body;
    }

    public void response(OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        writeResponseCodeHeader(dos);

        if (body != null) {
            writeResponseBody(dos, body);
        }
    }

    private void writeResponseCodeHeader(DataOutputStream dos) {
        try {
            String responseCodeHeader = String.format("HTTP/1.1 %s %s \r\n", responseCode.getStatusCode(), responseCode.getMessage());
            dos.writeBytes(responseCodeHeader);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " +  body.length + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
