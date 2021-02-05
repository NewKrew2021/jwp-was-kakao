package http.response;


import org.springframework.http.HttpStatus;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Map;

public class HttpResponse {
    public static final int FILE_TYPE = 1;
    private static final String START_LINE_FORMAT = "HTTP/1.1 %s\r\n";

    private final OutputStream out;
    private final ResponseHeaders headers = new ResponseHeaders();
    private byte[] body = new byte[0];

    public HttpResponse(OutputStream out) {
        this.out = out;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String path) {
        String fileType = path.split("\\.")[FILE_TYPE];

        ResponseFileType responseFileType = ResponseFileType.of(fileType);

        try {
            setBody(
                    FileIoUtils.loadFileFromClasspath(responseFileType.getFilePathPrefix() + path),
                    responseFileType.getContentType());

            ok();
        } catch (IOException e) {
            e.printStackTrace();
            internalServerError();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            badRequest();
        }
    }

    public void setBody(byte[] body, String contentType) {
        this.body = body;
        this.headers.put("Content-Type", contentType + ";charset=utf-8");
        this.headers.put("Content-Length", "" + body.length);
    }

    private void sendHttpResponse(String responseStartLine) {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes(responseStartLine);
            dos.writeBytes(getHeaders());
            dos.write(getBody(), 0, getBody().length);
            dos.flush();
        } catch (IOException e) {

        }
    }

    public void setSessionId(String sessionId) {
        headers.put("Set-Cookie", "SessionId=" + sessionId + "; Path=/");
    }

    public void ok() {
        sendHttpResponse(String.format(START_LINE_FORMAT, HttpStatus.OK));
    }

    public void sendRedirect(String path) {
        addHeader("Location", "http://localhost:8080" + path);
        sendHttpResponse(String.format(START_LINE_FORMAT, HttpStatus.FOUND));
    }

    public void badRequest() {
        sendHttpResponse(String.format(START_LINE_FORMAT, HttpStatus.BAD_REQUEST));
    }

    public void notFound() {
        sendHttpResponse(String.format(START_LINE_FORMAT, HttpStatus.NOT_FOUND));
    }

    public void internalServerError() {
        sendHttpResponse(String.format(START_LINE_FORMAT, HttpStatus.INTERNAL_SERVER_ERROR));
    }


    public String getHeaders() {
        StringBuilder message = new StringBuilder();

        for (Map.Entry<String, String> header : headers.entrySet()) {
            message.append(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        message.append("\r\n");

        return String.valueOf(message);
    }

    public byte[] getBody() {
        return body;
    }
}